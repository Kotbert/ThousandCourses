package kz.study.example.feature.home.di

import kz.study.example.core.database.repository.CourseRepository
import kz.study.example.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    single { CourseRepository(get(), get()) }
    viewModelOf(::HomeViewModel)
}
