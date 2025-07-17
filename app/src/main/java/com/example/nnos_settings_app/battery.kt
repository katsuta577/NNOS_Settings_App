package com.example.nnos_settings_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment

@Composable
fun BatterySettingsScreen() {
    var isBatterySaverOn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Battery", style = MaterialTheme.typography.headlineSmall)

        // バッテリーレベル（仮）
        Text("Battery level: 76%", style = MaterialTheme.typography.bodyLarge)

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

        // 使用状況
        Text("Battery usage", style = MaterialTheme.typography.titleMedium)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("・Display - 34%", style = MaterialTheme.typography.bodyMedium)
            Text("・System - 15%", style = MaterialTheme.typography.bodyMedium)
            Text("・Apps - 22%", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.weight(1f))

        // 詳細設定など
        Button(
            onClick = { /* 未実装 */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Battery Settings")
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_Battery_Preview_Phone() {
    BatterySettingsScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_Battery_Preview_Tablet() {
    BatterySettingsScreen()
}
