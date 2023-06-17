package com.nikitagorbatko.testhammersystems.domain

import com.nikitagorbatko.testhammersystems.data.DishesRepository

class GetDishesUseCase(private val repository: DishesRepository) {
    fun execute() = repository.getDishes()
}