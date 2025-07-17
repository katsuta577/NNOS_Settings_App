package com.example.nnos_settings_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AboutPhoneScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("端末情報", style = MaterialTheme.typography.headlineSmall)

        Text("デバイス名：NarikawaPhone", style = MaterialTheme.typography.bodyLarge)
        Text("モデル番号：NK-01", style = MaterialTheme.typography.bodyLarge)
        Text("Androidバージョン：14", style = MaterialTheme.typography.bodyLarge)
        Text("ビルド番号：NNOS.1.0.0", style = MaterialTheme.typography.bodyLarge)
        Text("カーネルバージョン：5.15.0-custom", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* ダミー処理 */ }, modifier = Modifier.fillMaxWidth()) {
            Text("ソフトウェア情報")
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_AboutPhone_Preview_Phone() {
    AboutPhoneScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_AboutPhone_Preview_Tablet() {
    AboutPhoneScreen()
}
