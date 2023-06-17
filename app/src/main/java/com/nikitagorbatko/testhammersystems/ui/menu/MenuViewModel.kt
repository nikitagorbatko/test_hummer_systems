package com.nikitagorbatko.testhammersystems.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikitagorbatko.testhammersystems.domain.GetDishesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel(
    private val getDishesUseCase: GetDishesUseCase
) : ViewModel() {
    val dishTags = mutableListOf<String>()

    val pagingDataFlow = getDishesUseCase.execute().cachedIn(viewModelScope)

    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

//    private val _categories = MutableSharedFlow<List<CategoryDto>>()
//    val categories = _categories.asSharedFlow()
//
//    fun getDishes(checkedChips: List<String>? = null) {
//        viewModelScope.launch {
//            _state.emit(State.LOADING)
//            try {
//                val categories = getCategoriesUseCase.invoke()
//                if (categories.isNotEmpty()) {
//                    if (checkedChips != null && checkedChips.isNotEmpty()) {
//                        _categories.emit(
//                            categories.filter {
//                                checkedChips.intersect(it.tags.toSet()).isNotEmpty()
//                            }
//                        )
//                    } else {
//                        _categories.emit(categories)
//                    }
//                    _state.emit(State.PRESENT)
//                } else {
//                    _state.emit(State.ERROR)
//                }
//            } catch (_: Exception) {
//                _state.emit(State.ERROR)
//            }
//        }
//    }

    enum class State {
        LOADING, PRESENT, ERROR
    }
}