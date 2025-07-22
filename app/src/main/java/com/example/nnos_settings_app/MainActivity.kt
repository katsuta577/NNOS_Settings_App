package com.example.nnos_settings_app

import WifiSelectorApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext


import android.Manifest
import android.os.Build
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_NETWORK_STATE
        )
        perms.forEach {
            if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, perms, 11)
        }
        setContent { AppNavigator() }
    }
}


@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "settings") {
        composable("settings") {
            SimpleSettingsScreen(navController)
        }

        composable("display") {
            DisplaySettingsScreaaaaaen()
        }

        composable("NNOS_Internet") {Bluetooth()}
    }
}

@Composable
fun SimpleSettingsScreen(navController: NavHostController) {
    val safeNavController = navController

    val isTablet = LocalConfiguration.current.screenWidthDp.dp > 600.dp

    val itemTitleFontSize = if (isTablet) 20.sp else 16.sp
    val itemSubtitleFontSize = if (isTablet) 15.sp else 13.sp
    val cardPadding = if (isTablet) 24.dp else 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
            .padding(horizontal = cardPadding, vertical = 24.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = if (isTablet) 32.sp else 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                SettingCard(
                    titleFontSize = itemTitleFontSize,
                    subtitleFontSize = itemSubtitleFontSize,
                    padding = cardPadding,
                    title = "Internet, and Network",
                    subtitle = "Internet, and Other Network Settings",
                    onClick = {safeNavController.navigate("NNOS_Internet")}
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
    padding: Dp,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = padding, vertical = 16.dp)
        ) {
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

@Composable
fun DisplaySettingsScreaaaaaen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("ディスプレイ設定画面", fontSize = 24.sp)
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800, name = "Phone Preview")
@Composable
fun NNOS_Settings_App_MainActivity_Preview_Phone() {
    val navController = rememberNavController()
    SimpleSettingsScreen(navController = navController)
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800, name = "Tablet Preview")
@Composable
fun NNOS_Settings_App_MainActivity_Preview_Tablet() {
    val navController = rememberNavController()
    SimpleSettingsScreen(navController = navController)
}
