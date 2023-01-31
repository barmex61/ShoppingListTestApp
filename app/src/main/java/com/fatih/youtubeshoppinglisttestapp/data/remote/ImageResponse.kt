package com.fatih.youtubeshoppinglisttestapp.data.remote

data class ImageResponse(
    val imageResults: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)