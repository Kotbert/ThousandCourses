package kz.study.example.feature.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.study.example.core.ui.components.CourseCard
import kz.study.example.feature.favorites.R
import kz.study.example.core.ui.R as CoreUiR
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    onCourseClick: (Int) -> Unit = {},
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val courses by viewModel.courses.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.favorites_title),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        if (courses.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.favorites_empty),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(courses, key = { it.id }) { course ->
                CourseCard(
                    title = course.title,
                    description = course.text,
                    price = course.price,
                    rate = course.rate,
                    startDate = course.startDate,
                    isFavorite = true,
                    onFavoriteClick = { viewModel.removeFromFavorites(course) },
                    onMoreClick = { onCourseClick(course.id) },
                    coverImage = when (course.id % 3) {
                        0 -> CoreUiR.drawable.course_cover_1
                        1 -> CoreUiR.drawable.course_cover_2
                        else -> CoreUiR.drawable.course_cover_3
                    }
                )
            }
        }
    }
}
