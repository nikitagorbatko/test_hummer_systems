package com.nikitagorbatko.testhammersystems.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DishesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dishes: List<DishDbo>)

    @Query("SELECT * FROM dishes ORDER BY millis ASC")//ordering is to help submitting (TO CHANGE)
    fun getDishes(): PagingSource<Int, DishDbo>

    @Query("DELETE FROM dishes")
    suspend fun clearDishes(): Int
}