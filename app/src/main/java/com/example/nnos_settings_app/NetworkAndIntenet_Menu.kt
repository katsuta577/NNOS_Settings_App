package com.example.nnos_settings_app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun InternetAndNetworkSettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "インターネット・通信",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        SettingToggleRow(
            title = "Wi-Fi",
            subtitle = "Wi-Fiネットワークに接続",
            checked = true,
            onCheckedChange = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingToggleRow(
            title = "Bluetooth",
            subtitle = "Bluetooth機器と接続",
            checked = false,
            onCheckedChange = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingToggleRow(
            title = "機内モード",
            subtitle = "すべての通信を無効化",
            checked = false,
            onCheckedChange = {}
        )
    }
}

@Composable
fun SettingToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_NetworkAndInternet_Menu_Preview_Phone() {
    InternetAndNetworkSettingsScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_NetworkAndInternet_Menu_Preview_Tablet() {
    InternetAndNetworkSettingsScreen()
}
