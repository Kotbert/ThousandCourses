package kz.study.example.core.network.mapper

import kz.study.example.core.network.dto.CourseDto
import kz.study.example.core.network.model.Course

fun CourseDto.toCourse() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)
