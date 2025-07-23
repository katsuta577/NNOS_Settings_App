package com.example.nnos_settings_app

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
fun BatterySettingsScreen1234() {
    val context = LocalContext.current
    var batteryLevel by remember { mutableStateOf(0) }
    var isBatterySaverOn by remember { mutableStateOf(false) }

    // バッテリーレベルの取得（1回だけ）
    LaunchedEffect(Unit) {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, intentFilter)
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        if (level in 0..100 && scale > 0) {
            batteryLevel = ((level.toFloat() / scale.toFloat()) * 100).toInt()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Battery", style = MaterialTheme.typography.headlineSmall)

        // 実際のバッテリーレベルを表示
        Text("Battery level: $batteryLevel%", style = MaterialTheme.typography.bodyLarge)

        // バッテリーセーバー
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

        // 使用状況（仮）
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
            onClick = { /* 未実装 */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Battery Settings")
        }
    }
}
