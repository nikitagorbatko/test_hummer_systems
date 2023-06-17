package com.nikitagorbatko.testhammersystems.api

import com.nikitagorbatko.testhammersystems.entity.Dish
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
//data class DishDto(
//    @Json(name = "id") override val id: Int,
//    @Json(name = "name") override val name: String? = null,
//    @Json(name = "price") override val price: Int? = null,
//    @Json(name = "weight") override val weight: Int? = null,
//    @Json(name = "description") override val description: String? = null,
//    @Json(name = "image_url") override val imageUrl: String? = null,
//    @Json(name = "tegs") override val tags: List<String> = listOf()
//) : Dish
//
//@JsonClass(generateAdapter = true)
//data class DishesResponseDto(
//    @Json(name = "dishes") val dishes: List<DishDto> = listOf()
//)

@JsonClass(generateAdapter = true)
data class DishesResponseDto(
    @Json(name = "count") val count: Int? = null,
    @Json(name = "results") val results: List<DishDto>
)

@JsonClass(generateAdapter = true)
data class DishDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String? = null,
    @Json(name = "thumbnail_url") override val thumbnailUrl: String? = null,
    @Json(name = "seo_title") override val seoTitle: String? = null,
    @Json(name = "description") override val description: String? = null,
): Dish