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
import com.example.initial.repositories.DonationsRepository
import com.example.initial.repositories.ExchangeableRepository
import com.example.initial.repositories.UserRepository
import com.example.initial.repositories.VoucherRepository
import com.example.initial.repositories.WalletRepository
import com.example.initial.screens.DonationsScreen
import com.example.initial.screens.GiveScreen
import com.example.initial.screens.HomeScreen
import com.example.initial.screens.LoginScreen
import com.example.initial.screens.VouchersScreen
import com.example.initial.screens.WalletScreen
import com.example.initial.screens.views.CameraViewModel
import com.example.initial.ui.theme.InitialTheme
import com.example.initial.viewmodels.donations.DonationsViewModel
import com.example.initial.viewmodels.donations.DonationsViewModelFactory
import com.example.initial.viewmodels.give.GiveViewModel
import com.example.initial.viewmodels.give.GiveViewModelFactory
import com.example.initial.viewmodels.login.LoginViewModel
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel
import com.example.initial.viewmodels.login.LoginViewModelFactory
import com.example.initial.viewmodels.vouchers.VouchersViewModel
import com.example.initial.viewmodels.vouchers.VouchersViewModelFactory
import com.example.initial.viewmodels.wallets.WalletViewModel
import com.example.initial.viewmodels.wallets.WalletViewModelFactory

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
            val iWallet = database.IWallet()
            val repository = VoucherRepository(iVoucher, iWallet, userSessionViewModel)
            val viewModel: VouchersViewModel =
                viewModel(factory = VouchersViewModelFactory(repository, userSessionViewModel))
            VouchersScreen(navController, viewModel)
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