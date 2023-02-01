package com.fatih.youtubeshoppinglisttestapp.data.remote

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)