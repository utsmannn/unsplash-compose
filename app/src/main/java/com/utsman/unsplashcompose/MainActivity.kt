package com.utsman.unsplashcompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.platform.setContent
import com.utsman.unsplashcompose.model.Result

class MainActivity : AppCompatActivity() {

    private val viewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainView()
        }
    }

    @Composable
    private fun mainView() {
        MaterialTheme {
            val state = viewModel.statePhoto.collectAsState()
            val lastIndex = state.value.lastIndex
            LazyColumnForIndexed(items = state.value) { i, photo ->
                when (photo) {
                    is Result.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Result.Photo -> {
                        ImageAsync(url = photo.urls.small)
                    }
                }

                if (lastIndex == i) {
                    onActive(callback = {
                        viewModel.getPhotos()
                        logi("aaaaa -> index isssss -- $i -- size -> ${state.value.size}")
                    })
                }
            }

        }
    }
}