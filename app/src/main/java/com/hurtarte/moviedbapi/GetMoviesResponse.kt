package com.hurtarte.moviedbapi

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
) {
}