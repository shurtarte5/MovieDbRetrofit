package com.hurtarte.moviedbapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetail : AppCompatActivity() {

    companion object{
        const val TAG = "MovieDetail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)


        val movie = intent.extras?.get(MainActivity.INTENT_MOVIE_KEY) as Movie
        title=movie.title


       Glide.with(this)
           .load("https://image.tmdb.org/t/p/w342${movie.backdropPath}")
           .transform(CenterCrop())
            .into(movie_backdrop)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            .transform(CenterCrop())
            .into(movie_poster)


        movie_title.text=movie.title
        movie_release_date.text=movie.releaseDate
        movie_overview.text=movie.overview
        movie_rating.rating=movie.rating/2






    }
}