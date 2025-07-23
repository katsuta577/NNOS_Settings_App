package com.example.nnos_settings_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DisplaySettingsScreen() {
    var brightness by remember { mutableFloatStateOf(0.5f) }
    var isDarkTheme by remember { mutableStateOf(false) }
    var selectedSleepTime by remember { mutableStateOf("30秒") }
    val sleepOptions = listOf("15秒", "30秒", "1分", "2分", "5分", "10分", "30分")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "ディスプレイ設定", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // 明るさスライダー
        Text(text = "明るさ", fontSize = 18.sp)
        Slider(
            value = brightness,
            onValueChange = { brightness = it },
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ダークテーマ切り替え
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "ダークテーマ", fontSize = 18.sp, modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // スリープ時間
        Text(text = "スリープまでの時間", fontSize = 18.sp)

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedSleepTime)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                sleepOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            selectedSleepTime = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_Display_Preview_Phone() {
    DisplaySettingsScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_Display_Preview_Tablet() {
    DisplaySettingsScreen()
}
