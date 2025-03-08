//package com.example.hanyarunrun.ui
//
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import kotlinx.coroutines.delay
//import com.example.hanyarunrun.R
//import com.example.hanyarunrun.ui.navigation.Screen
//
//@Composable
//fun SplashScreen(navController: NavController) {
//    var startAnimation by remember { mutableStateOf(false) }
//
//    val alphaAnim = animateFloatAsState(
//        targetValue = if (startAnimation) 1f else 0f,
//        animationSpec = androidx.compose.animation.core.tween(
//            durationMillis = 1500
//        )
//    )
//
//    LaunchedEffect(key1 = true) {
//        startAnimation = true
//        delay(2000) // Waktu tampilan splash screen (2 detik)
//        navController.navigate(Screen.Home.route) {
//            popUpTo(Screen.Splash.route) { inclusive = true }
//        }
//    }
//
//    // UI Splash Screen
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column( // ✅ Gunakan Column agar elemen tersusun secara vertikal
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.logo), // Ganti dengan logo aplikasimu
//                contentDescription = "Logo",
//                modifier = Modifier
//                    .size(150.dp)
//                    .alpha(alphaAnim.value)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp)) // ✅ Jarak antara gambar dan teks
//
//            Text(
//                text = "Muhamad Wahyu Maulana",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(8.dp)) // ✅ Jarak antar teks
//
//            Text(
//                text = "231511019",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
//
