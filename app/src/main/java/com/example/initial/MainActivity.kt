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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.initial.helpers.MyApplication
import com.example.initial.persistence.db.AppDatabase
import com.example.initial.repositories.UserRepository
import com.example.initial.screens.DonationsScreen
import com.example.initial.screens.GiveScreen
import com.example.initial.screens.HomeScreen
import com.example.initial.screens.LoginScreen
import com.example.initial.screens.WalletScreen
import com.example.initial.ui.theme.InitialTheme
import com.example.initial.viewmodels.LoginViewModel
import com.example.initial.viewmodels.factories.LoginViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this, lifecycleScope)

//        val database = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "app_database"
//        ).build()
        enableEdgeToEdge()
        setContent {
            InitialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigator(database)
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
fun AppNavigator(database: AppDatabase) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val iUser = database.IUser()
            val repository = UserRepository(iUser)
            val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(repository))
            LoginScreen(navController, viewModel)
        }
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