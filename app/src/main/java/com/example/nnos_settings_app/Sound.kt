package com.example.nnos_settings_app

import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun SoundSettingsScreen() {
    val isTablet = LocalConfiguration.current.screenWidthDp.dp > 600.dp
    val padding = if (isTablet) 32.dp else 24.dp
    val labelFontSize = if (isTablet) 18.sp else 14.sp

    val context = LocalContext.current
    val audioManager = remember {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
    val maxMediaVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    var selectedMode by remember { mutableStateOf("sound") }

    // メディア音量の状態をAudioManagerの現在値から初期化
    var mediaVolume by remember {
        mutableStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat())
    }
    var ringVolume by remember { mutableFloatStateOf(0.5f) }
    var notificationVolume by remember { mutableFloatStateOf(0.6f) }
    var systemVolume by remember { mutableFloatStateOf(0.4f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(padding)
    ) {
        Text(
            text = "サウンド設定",
            fontSize = if (isTablet) 28.sp else 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // ===== サウンドモード =====
        Text("サウンドモード", fontSize = labelFontSize, fontWeight = FontWeight.Medium)
        Column {
            listOf(
                "sound" to "サウンド",
                "vibrate" to "マナーモード（バイブ）",
                "silent" to "サイレント（バイブなし）"
            ).forEach { (value, label) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedMode == value,
                        onClick = { selectedMode = value }
                    )
                    Text(text = label, fontSize = labelFontSize)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== メディア音量スライダー（実際に音量を変える） =====
        SoundSlider(
            label = "メディア音量",
            value = mediaVolume / maxMediaVolume,
            onValueChange = {
                mediaVolume = it * maxMediaVolume
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    mediaVolume.toInt(),
                    AudioManager.FLAG_SHOW_UI
                )
            },
            labelFontSize = labelFontSize
        )

        // ===== その他の音量スライダー（UIのみ） =====
        SoundSlider("着信音", ringVolume, { ringVolume = it }, labelFontSize)
        SoundSlider("通知音", notificationVolume, { notificationVolume = it }, labelFontSize)
        SoundSlider("システム音", systemVolume, { systemVolume = it }, labelFontSize)
    }
}

@Composable
fun SoundSlider(label: String, value: Float, onValueChange: (Float) -> Unit, labelFontSize: TextUnit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, fontSize = labelFontSize, fontWeight = FontWeight.Medium)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Sound Settings Phone")
@Composable
fun SoundSettingsPreviewPhone() {
    SoundSettingsScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Sound Settings Tablet")
@Composable
fun SoundSettingsPreviewTablet() {
    SoundSettingsScreen()
}
