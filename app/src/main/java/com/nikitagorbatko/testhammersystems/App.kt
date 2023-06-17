package com.nikitagorbatko.testhammersystems

import android.app.Application
import com.nikitagorbatko.testhammersystems.api.Retrofit
import com.nikitagorbatko.testhammersystems.data.DishesRepository
import com.nikitagorbatko.testhammersystems.data.DishesRepositoryImpl
import com.nikitagorbatko.testhammersystems.database.DishesDatabase
import com.nikitagorbatko.testhammersystems.domain.GetDishesUseCase
import com.nikitagorbatko.testhammersystems.ui.menu.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    private val network = module {
        single { Retrofit() }
        single { get<Retrofit>().getDishesService() }
    }

    private val viewModels = module {
        viewModel { MenuViewModel(get()) }
    }

    private val database = module {
        single { DishesDatabase.getInstance(androidContext()) }
    }

    private val data = module {
        single<DishesRepository> { DishesRepositoryImpl(get(), get()) }
    }

    private val domain = module {
        factory { GetDishesUseCase(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                domain,
                database,
                viewModels,
                data,
                network
            )
        }
    }
}