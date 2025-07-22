package com.padieer.asesoriapp.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme

/**
 * Display an initial empty state or swipe to refresh content.
 *
 * @param empty (state) when true, display [emptyContent]
 * @param emptyContent (slot) the content to display for the empty state
 * @param loading (state) when true, display a loading spinner over [content]
 * @param onRefresh (event) event to request refresh
 * @param content (slot) the main content to show
 *
 * @author The Android Open Source Project
 * @see <a href="https://github.com/android/compose-samples/blob/master/JetNews/app/src/main/java/com/example/jetnews/ui/home/HomeScreens.kt#L393">JetNews App</a>
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit = { FullScreenLoading() },
    modifier: Modifier = Modifier
) {
    if (empty) {
        emptyContent()
    } else {
        val refreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            isRefreshing = loading,
            onRefresh = onRefresh,
            content = { content() },
            state = refreshState,
            indicator = {
                Indicator(
                    modifier = modifier
                        .align(Alignment.TopCenter)
                        .padding(),
                    isRefreshing = loading,
                    state = refreshState
                )
            }
        )
    }
}

@Composable
fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}