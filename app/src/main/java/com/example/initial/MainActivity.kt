package com.example.initial

import android.os.Bundle
import android.util.Log
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
import com.example.initial.persistence.entities.User
import com.example.initial.repositories.*
import com.example.initial.screens.*
import com.example.initial.ui.theme.InitialTheme
import com.example.initial.viewmodels.donations.DonationsViewModel
import com.example.initial.viewmodels.donations.DonationsViewModelFactory
import com.example.initial.viewmodels.give.GiveViewModel
import com.example.initial.viewmodels.give.GiveViewModelFactory
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel
import com.example.initial.viewmodels.leaderboard.LeaderboardViewModel
import com.example.initial.viewmodels.leaderboard.LeaderboardViewModelFactory
import com.example.initial.viewmodels.login.LoginViewModel
import com.example.initial.viewmodels.login.LoginViewModelFactory
import com.example.initial.viewmodels.vouchers.VouchersViewModel
import com.example.initial.viewmodels.vouchers.VouchersViewModelFactory
import com.example.initial.viewmodels.wallets.WalletViewModel
import com.example.initial.viewmodels.wallets.WalletViewModelFactory
import kotlinx.coroutines.launch


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

        // Ensure the test user exists in the database
        lifecycleScope.launch {
            val users = database.IUser().list()
            if (users.none { it.email == "user" && it.password == "123" }) {
                val user = User(
                    firstName = "Test",
                    lastName = "User",
                    email = "user",
                    password = "123",
                    isDeleted = 0
                )
                database.IUser().add(user)
                Log.d("MainActivity", "Test user added")
            } else {
                Log.d("MainActivity", "Test user already exists")
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
            val iVoucher = database.IVoucher()
            val categoryRepository = CategoryRepository(iCategory)
            val walletRepository = WalletRepository(iWallet, iVoucher, userSessionViewModel)
            val exchangeableRepository =
                ExchangeableRepository(iExchangeable, walletRepository, userSessionViewModel)
            val viewModel: GiveViewModel = viewModel(
                factory = GiveViewModelFactory(
                    categoryRepository, exchangeableRepository
                )
            )
            GiveScreen(navController, viewModel)
        }
        composable("donations") {
            val iExchangeable = database.IExchangeable()
            val repository = DonationsRepository(iExchangeable)
            val viewModel: DonationsViewModel =
                viewModel(factory = DonationsViewModelFactory(repository, userSessionViewModel))
            DonationsScreen(navController, viewModel)
        }
        composable("wallet") {
            val iWallet = database.IWallet()
            val iVoucher = database.IVoucher()
            val repository = WalletRepository(iWallet, iVoucher, userSessionViewModel)
            val viewModel: WalletViewModel = viewModel(factory = WalletViewModelFactory(repository))
            WalletScreen(navController, viewModel)
        }
        composable("vouchers") {
            val iVoucher = database.IVoucher()
            val repository = VoucherRepository(iVoucher)
            val viewModel: VouchersViewModel =
                viewModel(factory = VouchersViewModelFactory(repository, userSessionViewModel))
            VouchersScreen(navController, viewModel)
        }
        composable("leaderboard") {
            val iLeaderboard = database.ILeaderboard()
            val repository = LeaderboardRepository(iLeaderboard)
            val viewModel: LeaderboardViewModel =
                viewModel(factory = LeaderboardViewModelFactory(repository))
            LeaderboardScreen(navController, viewModel, userSessionViewModel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InitialTheme {
        Greeting("Android")
    }
}
