package com.example.initial.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.initial.persistence.entities.Category
import com.example.initial.persistence.entities.Exchangeable
import com.example.initial.persistence.entities.User
import com.example.initial.persistence.entities.Voucher
import com.example.initial.persistence.entities.Wallet
import com.example.initial.persistence.interfaces.ICategory
import com.example.initial.persistence.interfaces.IUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, Exchangeable::class, Wallet::class, Voucher::class, Category::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun IUser(): IUser
    abstract fun ICategory(): ICategory

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null;

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                ).addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
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
    }
}