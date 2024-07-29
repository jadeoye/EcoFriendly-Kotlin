package com.example.initial.persistence.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.initial.persistence.entities.*
import com.example.initial.persistence.interfaces.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Exchangeable::class, Wallet::class, Voucher::class, Category::class, Leaderboard::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun IUser(): IUser
    abstract fun ICategory(): ICategory
    abstract fun IExchangeable(): IExchangeable
    abstract fun IWallet(): IWallet
    abstract fun IVoucher(): IVoucher
    abstract fun ILeaderboard(): ILeaderboard

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().addCallback(AppDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateUsers(database.IUser())
                        populateCategories(database.ICategory())
                    }
                }
            }

            suspend fun populateUsers(iUser: IUser) {
                val user =
                    User(firstName = "User", lastName = "User", email = "user", password = "123")
                iUser.add(user)
                // Log to confirm user addition
                val users = iUser.list()
                users.forEach {
                    Log.d("AppDatabaseCallback", "User added: ${it.email}")
                }
            }

            suspend fun populateCategories(iCategory: ICategory) {
                var category = Category(name = "Shirts", points = 10)
                iCategory.add(category)

                category = Category(name = "Trousers", points = 5)
                iCategory.add(category)

                category = Category(name = "Skirts", points = 11)
                iCategory.add(category)
            }
        }
    }
}

