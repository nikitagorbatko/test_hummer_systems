package com.nikitagorbatko.testhammersystems.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikitagorbatko.testhammersystems.api.DishesService
import com.nikitagorbatko.testhammersystems.database.DishDbo
import com.nikitagorbatko.testhammersystems.database.DishesDatabase
import kotlinx.coroutines.flow.Flow

class DishesRepositoryImpl(
    private val service: DishesService,
    private val database: DishesDatabase
) : DishesRepository {
    override fun getDishes(): Flow<PagingData<DishDbo>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = DishesRemoteMediator.PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = DishesRemoteMediator(
                service = service,
                dishesDatabase = database
            ),
            pagingSourceFactory = { database.dishesDao().getDishes() }
        ).flow
    }
}