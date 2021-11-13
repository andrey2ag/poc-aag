package com.uvita.andrey.modules.repository.local

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uvita.andrey.BuildConfig
import com.uvita.andrey.models.entities.GitHubUser
import com.uvita.andrey.modules.repository.local.dao.GitHubDao

@Database(
    entities = [GitHubUser::class],
    version = 1
)
abstract class AppDB : RoomDatabase() {

    abstract fun githubDao(): GitHubDao

    companion object {
        @VisibleForTesting
        @JvmStatic
        lateinit var instance: AppDB
//            private set

        @JvmStatic
        fun createAppDatabase(context: Context) {
            synchronized(AppDB::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java, BuildConfig.DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
        }
    }
}
