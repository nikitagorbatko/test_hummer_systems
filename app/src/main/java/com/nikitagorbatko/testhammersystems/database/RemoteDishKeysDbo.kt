package com.nikitagorbatko.testhammersystems.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_dish_keys")
data class RemoteDishKeysDbo(
    @PrimaryKey
    val dishId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)