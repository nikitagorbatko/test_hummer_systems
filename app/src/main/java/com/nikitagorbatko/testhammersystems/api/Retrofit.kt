package com.nikitagorbatko.testhammersystems.api

import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_DISHES_URL = "https://tasty.p.rapidapi.com/"

class Retrofit {
    private val retrofitDishes = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_DISHES_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun getDishesService(): DishesService = retrofitDishes.create(DishesService::class.java)
}