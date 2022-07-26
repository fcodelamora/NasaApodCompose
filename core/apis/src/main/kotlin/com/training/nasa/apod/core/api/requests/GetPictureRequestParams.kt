package com.training.nasa.apod.core.api.requests

class GetPictureRequestParams(
    val date: String,
    private val apiKey: String
) : HashMap<String, String>() {
    init {
        put("date", date)
        put("hd", true.toString())
        put("thumbs", true.toString()) // receive thumbnails for videos
        put("api_key", apiKey)
    }
}
