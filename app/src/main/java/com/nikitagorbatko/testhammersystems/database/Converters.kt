package com.nikitagorbatko.testhammersystems.database

import androidx.room.TypeConverter

class Converters {
  @TypeConverter
  fun fromTimestamp(value: String?): List<String>? = value?.split(":")

  @TypeConverter
  fun dateToTimestamp(value: List<String>?): String? = value?.joinToString(separator = ":")
}