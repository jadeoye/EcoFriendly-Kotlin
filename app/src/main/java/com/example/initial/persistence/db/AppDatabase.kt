package com.example.initial.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.initial.persistence.entities.*
import com.example.initial.persistence.interfaces.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        User::class,
        Exchangeable::class,
        Wallet::class,
        Voucher::class,
        Category::class,
        Leaderboard::class
    ],
    version = 2, // Updated version number
    exportSchema = false
)
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
                // context.deleteDatabase("app_database")
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance.query("SELECT 1", null)
                instance
            }
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
                    populateLeaderboard(database.ILeaderboard()) // Populate leaderboard
                }
            }
        }

        suspend fun populateUsers(iUser: IUser) {
            val user = User(firstName = "User", lastName = "User", email = "user", password = "123")
            iUser.add(user)
        }

        suspend fun populateCategories(iCategory: ICategory) {
            var category = Category(name = "Shirts", points = 10)
            iCategory.add(category)

            category = Category(name = "Trousers", points = 5)
            iCategory.add(category)

            category = Category(name = "Skirts", points = 11)
            iCategory.add(category)
        }

        suspend fun populateLeaderboard(iLeaderboard: ILeaderboard) {
            val sampleLeaderboard = listOf(
                Leaderboard(name = "Alice", points = 150, createdOn = System.currentTimeMillis()),
                Leaderboard(name = "Charlie", points = 100, createdOn = System.currentTimeMillis()),
                Leaderboard(name = "Bob", points = 200, createdOn = System.currentTimeMillis()),
                Leaderboard(name = "Sodiq", points = 120, createdOn = System.currentTimeMillis()),
                Leaderboard(name = "Jeremiah", points = 140, createdOn = System.currentTimeMillis()),
                Leaderboard(name = "Angeline", points = 90, createdOn = System.currentTimeMillis())
            )
            sampleLeaderboard.forEach { leaderboard ->
                iLeaderboard.insert(leaderboard)
            }
        }
    }
}
