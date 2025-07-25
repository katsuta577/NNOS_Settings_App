package com.example.nnos_settings_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

@Composable
fun BatterySettingsScreen() {
    val context = LocalContext.current
    var batteryLevel by remember { mutableStateOf(0) }
    var isBatterySaverOn by remember { mutableStateOf(false) }

    // リアルタイムでバッテリーレベルを受け取る
    DisposableEffect(Unit) {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

                if (level in 0..100 && scale > 0) {
                    batteryLevel = ((level.toFloat() / scale.toFloat()) * 100).toInt()
                }
            }
        }

        context.registerReceiver(receiver, intentFilter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Battery", style = MaterialTheme.typography.headlineSmall)

        Text("Battery level: $batteryLevel%", style = MaterialTheme.typography.bodyLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Battery Saver", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = isBatterySaverOn,
                onCheckedChange = { isBatterySaverOn = it }
            )
        }

        Text("Battery usage", style = MaterialTheme.typography.titleMedium)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("・Display - 34%", style = MaterialTheme.typography.bodyMedium)
            Text("・System - 15%", style = MaterialTheme.typography.bodyMedium)
            Text("・Apps - 22%", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val intent = Intent(android.provider.Settings.ACTION_BATTERY_SAVER_SETTINGS)
                context.startActivity(intent)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Battery Settings")
        }
    }
}
