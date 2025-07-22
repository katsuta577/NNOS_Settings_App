package com.example.nnos_settings_app

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.Build
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun Bluetooth() {
    val context = LocalContext.current
    val bluetoothAdapter = remember { BluetoothAdapter.getDefaultAdapter() }
    val devices = remember { mutableStateListOf<BluetoothDevice>() }

    val isPermissionRequired = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val permissions = if (isPermissionRequired) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        emptyArray()
    }

    val permissionGranted = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    // ↓ ここで startDiscovery をラムダとして定義
    val startDiscovery = rememberUpdatedState {
        try {
            devices.clear()
            bluetoothAdapter?.cancelDiscovery()
            bluetoothAdapter?.startDiscovery()

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(ctx: Context?, intent: Intent?) {
                    val action = intent?.action
                    if (action == BluetoothDevice.ACTION_FOUND || action == BluetoothDevice.ACTION_NAME_CHANGED) {
                        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        device?.let {
                            val existing = devices.find { it.address == device.address }
                            if (existing == null) {
                                devices.add(device)
                            }
                        }
                    }
                }
            }

            val filter = IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothDevice.ACTION_NAME_CHANGED)
            }

            context.registerReceiver(receiver, filter)
        } catch (e: SecurityException) {
            Log.e("BluetoothScan", "Missing permissions: ${e.message}")
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms.all { it.value }) {
            startDiscovery.value()
        }
    }

    // ← ここで呼ぶときも .value() とする！
    LaunchedEffect(Unit) {
        if (isPermissionRequired && !permissionGranted) {
            permissionLauncher.launch(permissions)
        } else {
            startDiscovery.value()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Bluetooth Devices",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(
                devices
                    .filter { it.name?.isNotBlank() == true }
                    .sortedBy { it.name?.lowercase() }
            ) { device ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = device.name ?: "Unknown Device")
                    Text(
                        text = device.address,
                        style = MaterialTheme.typography.bodySmall
                    )

                    // ペアリング済みでなければボタンを表示
                    if (device.bondState != BluetoothDevice.BOND_BONDED) {
                        Button(
                            onClick = {
                                try {
                                    Log.d("Bluetooth", "Trying to pair with ${device.name}")
                                    val method = device.javaClass.getMethod("createBond")
                                    method.invoke(device)
                                } catch (e: Exception) {
                                    Log.e("Bluetooth", "Pairing failed: ${e.message}")
                                }
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("接続（ペアリング）")
                        }
                    }
                }
                Divider()
            }
        }
    }
}

