package com.example.nnos_settings_app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun unkoburiburi() {
    val isTablet = LocalConfiguration.current.screenWidthDp.dp > 600.dp

    val cardPadding = if (isTablet) 32.dp else 24.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
            .padding(horizontal = cardPadding, vertical = 24.dp)
    ) {
        Text(
            text = "Bluetooth機器の接続先",
            fontSize = if (isTablet) 32.sp else 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        // Bluetooth接続先カード
        BluetoothDeviceCard_Unpaired("Bluetooth Speaker A", isTablet)
        BluetoothDeviceCard_Paired("イヤホン Pro", isTablet)
    }
}

@Composable
fun BluetoothDeviceCard_Unpaired(deviceName: String, isTablet: Boolean) {
    val fontSize = if (isTablet) 18.sp else 14.sp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* ペアリング処理 */ },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Bluetooth",
                    tint = Color.Blue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = deviceName, fontSize = fontSize, fontWeight = FontWeight.Medium)
                    Text(text = "未ペアリング", fontSize = fontSize * 0.9, color = Color.Gray)
                }
            }
            Text(
                text = "ペアリング",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

@Composable
fun BluetoothDeviceCard_Paired(deviceName: String, isTablet: Boolean) {
    val fontSize = if (isTablet) 18.sp else 14.sp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* 詳細設定画面へ */ },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Bluetooth",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = deviceName, fontSize = fontSize, fontWeight = FontWeight.Medium)
                    Text(text = "接続済み", fontSize = fontSize * 0.9, color = Color.Gray)
                }
            }
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "詳細設定",
                tint = Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .alignByBaseline()
            )
        }
    }
}

// プレビュー：スマホ
@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_unko_Preview_Phone() {
    unkoburiburi()
}

// プレビュー：タブレット
@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_unko_Preview_Tablet() {
    unkoburiburi()
}
