package kz.study.example

import android.app.Application
import kz.study.example.core.database.di.databaseModule
import kz.study.example.core.network.di.networkModule
import kz.study.example.feature.detail.di.detailModule
import kz.study.example.feature.profile.di.profileModule
import kz.study.example.feature.favorites.di.favoritesModule
import kz.study.example.feature.home.di.homeModule
import kz.study.example.feature.login.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                databaseModule,
                loginModule,
                homeModule,
                favoritesModule,
                detailModule,
                profileModule
            )
        }
    }
}
