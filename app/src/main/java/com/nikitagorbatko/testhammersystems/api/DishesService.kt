package com.nikitagorbatko.testhammersystems.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DishesService {
    @Headers(
        "X-RapidAPI-Key: fc3f2a63a5msh57aed46d5aedc49p1ec313jsn161062a216ae",
        "X-RapidAPI-Host: tasty.p.rapidapi.com"
    )
    @GET("recipes/list")
    suspend fun getDishes(@Query("from") from: Int, @Query("size") size: Int): DishesResponseDto
}