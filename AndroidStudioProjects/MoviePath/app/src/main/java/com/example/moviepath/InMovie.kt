package com.example.moviepath
import com.google.gson.annotations.SerializedName

class InMovie {
    @SerializedName("poster_path")
    var poster: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var overview: String? = null
}