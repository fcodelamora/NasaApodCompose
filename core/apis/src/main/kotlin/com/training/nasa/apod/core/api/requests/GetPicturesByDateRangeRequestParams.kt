package com.training.nasa.apod.core.api.requests

class GetPicturesByDateRangeRequestParams(
    val startDate: String,
    val endDate: String,
    private val apiKey: String
) : HashMap<String, String>() {
    init {
        put("start_date", startDate)
        put("end_date", endDate)
        put("hd", true.toString())
        put("thumbs", true.toString()) // receive thumbnails for videos
        put("api_key", apiKey)
    }
}
