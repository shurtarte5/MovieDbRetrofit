package com.hurtarte.moviedbapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "3abc5f93e76fd7c3dbdfd790df363d4a",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "3abc5f93e76fd7c3dbdfd790df363d4a",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}