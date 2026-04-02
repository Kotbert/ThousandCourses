package kz.study.example.core.network.api

import kz.study.example.core.network.dto.CoursesResponse
import retrofit2.http.GET

interface CourseApi {
    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponse
}
