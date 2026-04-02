package kz.study.example.core.database.mapper

import kz.study.example.core.database.entity.CourseEntity
import kz.study.example.core.network.model.Course

fun CourseEntity.toCourse() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = true,
    publishDate = publishDate
)

fun Course.toEntity() = CourseEntity(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    publishDate = publishDate
)
