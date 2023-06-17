package com.nikitagorbatko.testhammersystems.data

import androidx.paging.PagingData
import com.nikitagorbatko.testhammersystems.api.DishesResponseDto
import com.nikitagorbatko.testhammersystems.database.DishDbo
import kotlinx.coroutines.flow.Flow

interface DishesRepository {
    fun getDishes(): Flow<PagingData<DishDbo>>
}