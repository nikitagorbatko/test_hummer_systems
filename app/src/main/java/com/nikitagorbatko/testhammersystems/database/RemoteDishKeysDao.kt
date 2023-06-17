package com.nikitagorbatko.testhammersystems.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteDishKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteDishKeysDbo>)

    @Query("SELECT * FROM remote_dish_keys WHERE dishId = :dishId")
    suspend fun remoteKeysDishId(dishId: Int): RemoteDishKeysDbo?

    @Query("DELETE FROM remote_dish_keys")
    suspend fun clearRemoteKeys()
}