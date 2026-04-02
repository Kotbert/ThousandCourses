package kz.study.example.feature.detail.di

import kz.study.example.feature.detail.DetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val detailModule = module {
    viewModelOf(::DetailViewModel)
}
