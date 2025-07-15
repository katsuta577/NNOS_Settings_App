package com.example.nnos_settings_app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun SimpleSettingsScreen() {
    val isTablet = LocalConfiguration.current.screenWidthDp.dp > 600.dp

    val itemTitleFontSize = if (isTablet) 20.sp else 16.sp
    val itemSubtitleFontSize = if (isTablet) 15.sp else 13.sp
    val cardPadding = if (isTablet) 24.dp else 16.dp

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFD3D3D3)).padding(horizontal = cardPadding, vertical = 24.dp)) {

        Text(
            text = "設定",
            fontSize = if (isTablet) 32.sp else 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                SettingCard(titleFontSize = itemTitleFontSize, subtitleFontSize = itemSubtitleFontSize, padding = cardPadding,
                    title = "インターネット、通信",
                    subtitle = "インターネット、モバイルネットワーク、その他通信の設定"
                )
            }

            item {
                SettingCard(titleFontSize = itemTitleFontSize, subtitleFontSize = itemSubtitleFontSize, padding = cardPadding,
                    title = "サウンド、バイブレーション",
                    subtitle = "音量、バイブレーションの切り替え、通知・着信音"
                )
            }

            item {
                SettingCard(titleFontSize = itemTitleFontSize, subtitleFontSize = itemSubtitleFontSize, padding = cardPadding,
                    title = "ディスプレイ",
                    subtitle = "輝度"
                )
            }

            item {
                SettingCard(titleFontSize = itemTitleFontSize, subtitleFontSize = itemSubtitleFontSize, padding = cardPadding,
                    title = "カスタマイズ",
                    subtitle = "壁紙、通知・着信音、テーマの設定"
                )
            }

            item {
                SettingCard(titleFontSize = itemTitleFontSize, subtitleFontSize = itemSubtitleFontSize, padding = cardPadding,
                    title = "その他",
                    subtitle = "アプリ、バッテリー、ストレージ、セキュリティ、システム情報の設定"
                )
            }
        }
    }
}

@Composable
fun SettingCard(
    title: String,
    subtitle: String,
    titleFontSize: TextUnit,
    subtitleFontSize: TextUnit,
    padding: Dp
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { /* ここに処理 */ }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = padding, vertical = 16.dp)) {

            Text(
                text = title,
                fontSize = titleFontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = subtitleFontSize,
                color = Color(0xFF5A5A5A)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_MainActivity_Preview_Phone() {
    SimpleSettingsScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_MainActivity_Preview_Tablet() {
    SimpleSettingsScreen()
}
