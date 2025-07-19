package com.example.nnos_settings_app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

data class WiFiItem(val ssid: String, val connected: Boolean)

val sampleWifiList = listOf(
    WiFiItem("HomeWiFi", connected = true),
    WiFiItem("Cafe_WiFi", connected = false),
    WiFiItem("OfficeNetwork", connected = false),
    WiFiItem("PublicWiFi", connected = false)
)

@Composable
fun WiFiDetailScreen() {
    val config = LocalConfiguration.current
    val isTablet = config.screenWidthDp >= 600

    val titleFontSize = if (isTablet) 32.sp else 24.sp
    val itemFontSize = if (isTablet) 20.sp else 16.sp
    val verticalPadding = if (isTablet) 32.dp else 32.dp
    val itemSpacing = if (isTablet) 20.dp else 12.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 32.dp)
    ) {
        Text(
            text = "Wi-Fi設定",
            fontSize = titleFontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = verticalPadding)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(itemSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(sampleWifiList) { wifi ->
                WiFiListItem(
                    ssid = wifi.ssid,
                    connected = wifi.connected,
                    fontSize = itemFontSize,
                    onClick = { /* 後で接続処理 */ }
                )
            }
        }
    }
}

@Composable
fun WiFiListItem(
    ssid: String,
    connected: Boolean,
    fontSize: TextUnit,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (connected) Color(0xFFE0F7FA) else Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ssid,
                fontWeight = if (connected) FontWeight.Bold else FontWeight.Normal,
                fontSize = fontSize,
                modifier = Modifier.weight(1f)
            )
            if (connected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "接続中",
                    tint = Color(0xFF00796B)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "WiFi Detail Phone")
@Composable
fun PreviewWiFiDetailScreenPhone() {
    WiFiDetailScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "WiFi Detail Tablet")
@Composable
fun PreviewWiFiDetailScreenTablet() {
    WiFiDetailScreen()
}
