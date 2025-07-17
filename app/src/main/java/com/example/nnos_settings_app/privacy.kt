package com.example.nnos_settings_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment

@Composable
fun PrivacySettingsScreen() {
    var locationEnabled by remember { mutableStateOf(true) }
    var micAccess by remember { mutableStateOf(true) }
    var cameraAccess by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("プライバシー設定", style = MaterialTheme.typography.headlineSmall)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("位置情報", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Switch(checked = locationEnabled, onCheckedChange = { locationEnabled = it })
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("マイクの使用", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Switch(checked = micAccess, onCheckedChange = { micAccess = it })
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("カメラの使用", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Switch(checked = cameraAccess, onCheckedChange = { cameraAccess = it })
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_Privacy_Preview_Phone() {
    PrivacySettingsScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_Privacy_Preview_Tablet() {
    PrivacySettingsScreen()
}
