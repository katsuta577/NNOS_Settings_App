package com.example.nnos_settings_app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DisplaySettingsScreen() {
    val context = LocalContext.current
    var brightness by remember { mutableFloatStateOf(getCurrentBrightness(context) / 255f) }
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
            onValueChange = {
                brightness = it
                if (Settings.System.canWrite(context)) {
                    setScreenBrightness(context, it)
                } else {
                    Toast.makeText(context, "システム設定の変更を許可してください", Toast.LENGTH_SHORT).show()
                    requestWriteSettingsPermission(context)
                }
            },
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "${(brightness * 100).toInt()}%", fontSize = 14.sp)

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

// 🔧 実際の輝度をシステムに反映
fun setScreenBrightness(context: Context, brightness: Float) {
    val brightnessValue = (brightness * 255).toInt().coerceIn(0, 255)
    Settings.System.putInt(
        context.contentResolver,
        Settings.System.SCREEN_BRIGHTNESS,
        brightnessValue
    )
}

// 🔄 現在の輝度を取得（0〜255）
fun getCurrentBrightness(context: Context): Int {
    return try {
        Settings.System.getInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS
        )
    } catch (e: Settings.SettingNotFoundException) {
        128 // デフォルト値
    }
}

// 🔐 許可がない場合、設定画面へ誘導
fun requestWriteSettingsPermission(context: Context) {
    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
    intent.data = Uri.parse("package:${context.packageName}")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_Display_Preview_Phone() {
    DisplaySettingsScreen()
}
