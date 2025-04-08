package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nomad.ui.navigation.BottomNavigationBar
import com.example.nomad.ui.navigation.Navigation  // ✅ Use this instead
import com.example.nomad.ui.navigation.NavRoutes
import com.nomad.viewmodel.AuthViewModel
import com.example.nomad.viewmodel.FollowViewModel

@Composable
fun NomadApp() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel() // Replace with hiltViewModel() if using Hilt
    val followViewModel = FollowViewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Show BottomNav only if NOT on Login or Register screen
    val showBottomBar = currentRoute != NavRoutes.Login.route && currentRoute != NavRoutes.Register.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->
        Box(modifier = androidx.compose.ui.Modifier.padding(padding)) {
            // ✅ Plug in the correct Navigation function
            Navigation(navController, authViewModel, followViewModel)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomadApp()
        }
    }
}
