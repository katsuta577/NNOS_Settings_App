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
    val maxRingVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)

    var selectedMode by remember { mutableStateOf("sound") }

    var mediaVolume by remember {
        mutableStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat())
    }

    var ringVolume by remember {
        mutableStateOf(
            audioManager.getStreamVolume(AudioManager.STREAM_RING).toFloat()
        )
    }

    val maxNotificationVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)

    var notificationVolume by remember {
        mutableStateOf(
            audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION).toFloat()
        )
    }

    val maxAlarmVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)

    var alarmVolume by remember {
        mutableStateOf(
            audioManager.getStreamVolume(AudioManager.STREAM_ALARM).toFloat()
        )
    }

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
                        onClick = {
                            selectedMode = value
                            when (value) {
                                "sound" -> audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                                "vibrate" -> audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                                "silent" -> audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                            }
                        }
                    )
                    Text(text = label, fontSize = labelFontSize)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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

        // ✅ 着信音量スライダー（実際に変更）
        SoundSlider(
            label = "着信音",
            value = ringVolume / maxRingVolume,
            onValueChange = {
                ringVolume = it * maxRingVolume
                audioManager.setStreamVolume(
                    AudioManager.STREAM_RING,
                    ringVolume.toInt(),
                    AudioManager.FLAG_SHOW_UI
                )
            },
            labelFontSize = labelFontSize
        )

        // 他はまだUIだけ（通知音、システム音）
        SoundSlider(
            label = "通知音",
            value = notificationVolume / maxNotificationVolume,
            onValueChange = {
                notificationVolume = it * maxNotificationVolume
                audioManager.setStreamVolume(
                    AudioManager.STREAM_NOTIFICATION,
                    notificationVolume.toInt(),
                    AudioManager.FLAG_SHOW_UI
                )
            },
            labelFontSize = labelFontSize
        )

        SoundSlider(
            label = "アラーム音",
            value = alarmVolume / maxAlarmVolume,
            onValueChange = {
                alarmVolume = it * maxAlarmVolume
                audioManager.setStreamVolume(
                    AudioManager.STREAM_ALARM,
                    alarmVolume.toInt(),
                    AudioManager.FLAG_SHOW_UI
                )
            },
            labelFontSize = labelFontSize
        )
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
