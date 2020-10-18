package com.utsman.unsplashcompose

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.stateFor
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.Dp
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*

@Composable
fun ImageAsync(url: String, modifier: Modifier = Modifier.fillMaxWidth(), onImageLoaded: ((Boolean) -> Unit)? = null) {
    MaterialTheme {
        val context = ContextAmbient.current
        val imageAsset = stateFor<ImageAsset?>(null) { null }
        val glide = Glide.with(context)
        val job = CoroutineScope(Dispatchers.IO)
        val target = object : CustomTarget<Bitmap>() {
            override fun onResourceReady(bitmap: Bitmap, p1: Transition<in Bitmap>?) {
                val asset = bitmap.asImageAsset()
                imageAsset.value = asset
                onImageLoaded?.invoke(true)
            }

            override fun onLoadCleared(drawable: Drawable?) {
                val clearAsset = drawable?.toBitmap()?.asImageAsset()
                if (clearAsset != null)
                    imageAsset.value = clearAsset
            }

            override fun onLoadStarted(placeholder: Drawable?) {
                super.onLoadStarted(placeholder)
                val placeholderAsset = placeholder?.toBitmap()?.asImageAsset()
                if (placeholderAsset != null)
                    imageAsset.value = placeholderAsset
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                val errorAsset = errorDrawable?.toBitmap()?.asImageAsset()
                if (errorAsset != null)
                    imageAsset.value = errorAsset
                onImageLoaded?.invoke(false)
            }
        }

        onCommit(callback = {
            job.launch {
                glide
                    .asBitmap()
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(target)

                target.getSize { width, height ->
                    modifier.width(Dp(width.toFloat()))
                        .height(Dp(height.toFloat()))
                }
            }
        })

        onDispose(callback = {
            glide.clear(target)
            imageAsset.value = null
            job.cancel()
        })

        val asset = imageAsset.value
        if (asset != null)
            Image(asset = asset, contentScale = ContentScale.Crop, modifier = modifier)
    }
}