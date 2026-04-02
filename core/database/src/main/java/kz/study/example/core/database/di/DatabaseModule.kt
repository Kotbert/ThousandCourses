package kz.study.example.core.database.di

import android.content.Context
import androidx.room.Room
import kz.study.example.core.database.AppDatabase
import kz.study.example.core.database.dao.CourseDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "thousand_courses.db"
        ).build()
    }

    single<CourseDao> {
        get<AppDatabase>().courseDao()
    }
}
