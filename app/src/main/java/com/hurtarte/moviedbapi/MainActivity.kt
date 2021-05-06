package com.hurtarte.moviedbapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}
class MainActivity : AppCompatActivity() {
    private lateinit var popularMovies: RecyclerView
    private lateinit var topratedMovies:RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var topratedMoviesAdapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popularMovies= findViewById(R.id.popular_movies)
        topratedMovies= findViewById(R.id.toprated_movies)

        popularMovies.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        topratedMovies.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        popularMoviesAdapter=MoviesAdapter(listOf()) { movieItem: Movie ->
            movieItemClicked(
                movieItem
            )
        }
        popularMovies.adapter=popularMoviesAdapter

        topratedMoviesAdapter= MoviesAdapter(listOf()) { movieItem: Movie ->
            movieItemClicked(
                movieItem
            )
        }
        topratedMovies.adapter=topratedMoviesAdapter

        MovieRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )

        MovieRepository.getTopRatedMovies(
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError
        )

        //setup usuario

        val bundle = intent.extras
        val email=bundle?.getString("email")
        val provider=bundle?.getString("provider")

        setup(email?: "",provider?: "")

        //Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE)
            .edit()
        prefs.putString("email",email)
        prefs.putString("provider", provider)
        prefs.apply()



    }

    private fun setup(email:String, provider:String) {
        email_textview_mainactivity.text=email
        provider_textview_mainactivity.text=provider

        logout_button.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE)
                .edit()
                prefs.clear()
                prefs.apply()

            if(provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }



            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        // Log.d("MainActivity", "Movies: $movies")
        popularMoviesAdapter.updateMovies(movies)
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        // Log.d("MainActivity", "Movies: $movies")
        topratedMoviesAdapter.updateMovies(movies)
    }

    private fun onError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun movieItemClicked(movie: Movie){
        //Toast.makeText(this,"clicked: ${movie.title}" , Toast.LENGTH_LONG).show()
        val movieDetailIntent=Intent(this,MovieDetail::class.java)
        movieDetailIntent.putExtra(INTENT_MOVIE_KEY,movie)
        startActivity(movieDetailIntent)
    }

    companion object{
        const val INTENT_MOVIE_KEY="movie"
    }
}