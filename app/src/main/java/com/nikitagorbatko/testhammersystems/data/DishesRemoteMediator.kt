package com.nikitagorbatko.testhammersystems.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nikitagorbatko.testhammersystems.api.DishesService
import com.nikitagorbatko.testhammersystems.database.DishDbo
import com.nikitagorbatko.testhammersystems.database.DishesDatabase
import com.nikitagorbatko.testhammersystems.database.RemoteDishKeysDbo
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar

@OptIn(ExperimentalPagingApi::class)
class DishesRemoteMediator(
    private val service: DishesService,
    private val dishesDatabase: DishesDatabase
) : RemoteMediator<Int, DishDbo>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DishDbo>
    ): MediatorResult {
        var crutchCounter = 0

        val from = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(PAGE_SIZE) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val dishes = service.getDishes(from = from, size = PAGE_SIZE).results

            val endOfPaginationReached = dishes.isEmpty() || dishes.size < PAGE_SIZE
            dishesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dishesDatabase.remoteDishKeysDao().clearRemoteKeys()
                    dishesDatabase.dishesDao().clearDishes()
                }
                val prevKey = if (from == 0) null else from - PAGE_SIZE
                val nextKey = if (endOfPaginationReached) null else from + PAGE_SIZE
                val keys = dishes.map { dish ->
                    Log.d("NEXT", "id=${dish.id}, prev=$prevKey, next=$nextKey")
                    RemoteDishKeysDbo(dishId = dish.id, prevKey = prevKey, nextKey = nextKey)
                }
                dishesDatabase.remoteDishKeysDao().insertAll(keys)
                val dboDishes = dishes.map {
                    DishDbo(
                        id = it.id,
                        name = it.name,
                        thumbnailUrl = it.thumbnailUrl,
                        seoTitle = it.seoTitle,
                        description = it.description,
                        millis = System.currentTimeMillis() + ++crutchCounter
                    )
                }
                dishesDatabase.dishesDao().insertAll(dboDishes)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DishDbo>): RemoteDishKeysDbo? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { dish ->
                dishesDatabase.remoteDishKeysDao().remoteKeysDishId(dish.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DishDbo>): RemoteDishKeysDbo? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { dish ->
                dishesDatabase.remoteDishKeysDao().remoteKeysDishId(dish.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, DishDbo>
    ): RemoteDishKeysDbo? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { dishId ->
                dishesDatabase.remoteDishKeysDao().remoteKeysDishId(dishId)
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}