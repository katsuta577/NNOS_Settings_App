package com.example.nnos_settings_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StorageSettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("ストレージ設定", style = MaterialTheme.typography.headlineSmall)

        // 使用容量表示
        Text("使用済み: 24.5 GB / 64 GB", style = MaterialTheme.typography.bodyLarge)
        LinearProgressIndicator(progress = 24.5f / 64f, modifier = Modifier.fillMaxWidth())

        // カテゴリ別使用量
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("・アプリ：10.2 GB", style = MaterialTheme.typography.bodyMedium)
            Text("・画像と動画：8.1 GB", style = MaterialTheme.typography.bodyMedium)
            Text("・システム：6.0 GB", style = MaterialTheme.typography.bodyMedium)
        }

        // クリーンアップボタン
        Button(
            onClick = { /* ダミー処理 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("クリーンアップ")
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_Storage_Preview_Phone() {
    StorageSettingsScreen()
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_Storage_Preview_Tablet() {
    StorageSettingsScreen()
}
