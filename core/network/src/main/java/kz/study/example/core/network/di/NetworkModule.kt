package kz.study.example.core.network.di

import kz.study.example.core.network.api.CourseApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/u/0/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CourseApi> {
        get<Retrofit>().create(CourseApi::class.java)
    }
}
