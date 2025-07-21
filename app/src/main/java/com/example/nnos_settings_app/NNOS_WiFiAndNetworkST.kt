// 必要なimportは省略
import android.Manifest
import android.content.Context
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WifiSelectorApp(context: Context) {
    var selectedNetwork by remember { mutableStateOf<ScanResult?>(null) }
    var password by remember { mutableStateOf("") }
    var wifiList by remember { mutableStateOf<List<ScanResult>>(emptyList()) }

    // パーミッション
    val activity = context as? ComponentActivity
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity?.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            activity?.requestPermissions(arrayOf(Manifest.permission.CHANGE_NETWORK_STATE), 0)
        }
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.startScan()
        wifiList = wifiManager.scanResults
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (selectedNetwork == null) {
            Column(Modifier.padding(16.dp)) {
                Text("Wi-Fi一覧", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                LazyColumn {
                    items(wifiList) { scan ->
                        Text(
                            text = scan.SSID.ifEmpty { "(SSID非公開)" },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedNetwork = scan }
                                .padding(8.dp)
                        )
                    }
                }
            }
        } else {
            val isSecured =
                selectedNetwork?.capabilities?.contains("WEP") == true ||
                        selectedNetwork?.capabilities?.contains("WPA") == true
            Column(Modifier.padding(16.dp)) {
                Text("SSID: ${selectedNetwork!!.SSID}")
                Spacer(Modifier.height(10.dp))
                if (isSecured) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("パスワード") }
                    )
                    Spacer(Modifier.height(10.dp))
                } else {
                    // オープンネットワークならパスワード欄なし
                    Text("オープンネットワーク（パスワード不要）")
                    Spacer(Modifier.height(10.dp))
                }
                Button(onClick = {
                    connectWifi(context, selectedNetwork!!.SSID, if(isSecured) password else null)
                }) { Text("接続") }
                Spacer(Modifier.height(10.dp))
                Button(onClick = { selectedNetwork = null; password = "" }) { Text("戻る") }
            }
        }
    }
}

// セキュリティありなら password, なしなら null
@RequiresApi(Build.VERSION_CODES.Q)
fun connectWifi(context: Context, ssid: String, password: String?) {
    val builder = WifiNetworkSpecifier.Builder()
        .setSsid(ssid)
    if (!password.isNullOrEmpty()) builder.setWpa2Passphrase(password)

    val wifiNetworkSpecifier = builder.build()
    val networkRequest = NetworkRequest.Builder()
        .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
        .setNetworkSpecifier(wifiNetworkSpecifier)
        .build()
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.requestNetwork(networkRequest, object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: android.net.Network) {
            connectivityManager.bindProcessToNetwork(network)
            Toast.makeText(context, "接続成功", Toast.LENGTH_SHORT).show()
        }
        override fun onUnavailable() {
            Toast.makeText(context, "接続失敗", Toast.LENGTH_SHORT).show()
        }
    })
}
