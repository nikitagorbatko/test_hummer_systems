package com.nikitagorbatko.testhammersystems.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitagorbatko.testhammersystems.entity.Dish

@Entity(tableName = "dishes")
data class DishDbo(
    @PrimaryKey
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "name") override val name: String? = null,
    @ColumnInfo(name = "thumbnail_url") override val thumbnailUrl: String? = null,
    @ColumnInfo(name = "seo_title") override val seoTitle: String? = null,
    @ColumnInfo(name = "description") override val description: String? = null,
    @ColumnInfo(name = "millis") val millis: Long,
) : Dish