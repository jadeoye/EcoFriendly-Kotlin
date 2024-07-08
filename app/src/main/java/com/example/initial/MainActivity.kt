package com.example.initial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.example.initial.persistence.db.AppDatabase
import com.example.initial.repositories.CategoryRepository
import com.example.initial.repositories.ExchangeableRepository
import com.example.initial.repositories.UserRepository
import com.example.initial.repositories.WalletRepository
import com.example.initial.screens.DonationsScreen
import com.example.initial.screens.GiveScreen
import com.example.initial.screens.HomeScreen
import com.example.initial.screens.LoginScreen
import com.example.initial.screens.WalletScreen
import com.example.initial.screens.views.CameraViewModel
import com.example.initial.ui.theme.InitialTheme
import com.example.initial.viewmodels.give.GiveViewModel
import com.example.initial.viewmodels.give.GiveViewModelFactory
import com.example.initial.viewmodels.login.LoginViewModel
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel
import com.example.initial.viewmodels.login.LoginViewModelFactory

class MainActivity : ComponentActivity() {
    private val userSessionViewModel: UserSessionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this, lifecycleScope)
        enableEdgeToEdge()
        setContent {
            InitialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigator(database, userSessionViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigator(database: AppDatabase, userSessionViewModel: UserSessionViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val iUser = database.IUser()
            val repository = UserRepository(iUser)
            val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(repository))
            LoginScreen(navController, viewModel, userSessionViewModel)
        }
        composable("home") { HomeScreen(navController, userSessionViewModel) }
        composable("give") {
            val iWallet = database.IWallet()
            val iCategory = database.ICategory()
            val iExchangeable = database.IExchangeable()
            val categoryRepository = CategoryRepository(iCategory)
            val walletRepository = WalletRepository(iWallet, userSessionViewModel)
            val exchangeableRepository =
                ExchangeableRepository(iExchangeable, walletRepository, userSessionViewModel)
            val viewModel: GiveViewModel = viewModel(
                factory = GiveViewModelFactory(
                    categoryRepository,
                    exchangeableRepository
                )
            )
            GiveScreen(navController, viewModel)
        }
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