package com.nikitagorbatko.testhammersystems.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DishDbo::class, RemoteDishKeysDbo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DishesDatabase : RoomDatabase() {

    abstract fun dishesDao(): DishesDao
    abstract fun remoteDishKeysDao(): RemoteDishKeysDao

    companion object {

        @Volatile
        private var INSTANCE: DishesDatabase? = null

        fun getInstance(context: Context): DishesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DishesDatabase::class.java, "dishes_database.db"
            )
                .build()
    }
}