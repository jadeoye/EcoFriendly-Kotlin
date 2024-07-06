package com.example.initial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.initial.helpers.MyApplication
import com.example.initial.screens.DonationsScreen
import com.example.initial.screens.GiveScreen
import com.example.initial.screens.HomeScreen
import com.example.initial.screens.LoginScreen
import com.example.initial.screens.WalletScreen
import com.example.initial.ui.theme.InitialTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val database = (application as MyApplication).database
//
//        lifecycleScope.launch {  }

        enableEdgeToEdge()
        setContent {
            InitialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigator()
                }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Jeremiah",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun AppNavigator(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("give") { GiveScreen(navController) }
        composable("donations") { DonationsScreen(navController) }
        composable("wallet") { WalletScreen(navController) }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InitialTheme {
        Greeting("Android")
    }
}