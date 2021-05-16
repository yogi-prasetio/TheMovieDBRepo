package com.dicoding.jetpack.repository.data.source.remote

import com.dicoding.jetpack.repository.data.api.Client
import com.dicoding.jetpack.repository.data.source.remote.reponse.MovieResponse
import com.dicoding.jetpack.repository.data.source.remote.reponse.TvShowResponse
import com.dicoding.jetpack.repository.utils.EspressoIdlingResource
import retrofit2.await

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply { instance = this }
            }
    }

    suspend fun getPopularMovie(callback: LoadPopularMoviesCallback) {
        EspressoIdlingResource.increment()
        Client.apiInstance.getPopularMovies().await().results.let { listMovie ->
            callback.onAllMoviesReceived(listMovie)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getPopularTvShow(callback: LoadPopularTvShowCallback) {
        EspressoIdlingResource.increment()
        Client.apiInstance.getPopularTv().await().results.let { listTvShow ->
            callback.onAllTvShowsReceived(listTvShow)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getMovieDetail(movieId: Int, callback: LoadMovieDetailCallback) {
        EspressoIdlingResource.increment()
        Client.apiInstance.getMovieDetail(movieId).await().let {
            movie -> callback.onMovieDetailReceived(movie)
                     EspressoIdlingResource.decrement()
        }
    }

    suspend fun getTvShowDetail(tvShowId: Int, callback: LoadTvShowDetailCallback) {
        EspressoIdlingResource.increment()
        Client.apiInstance.getTvDetail(tvShowId).await().let {
            tvShow -> callback.onTvShowDetailReceived(tvShow)
                      EspressoIdlingResource.decrement()
        }
    }

    interface LoadPopularMoviesCallback {
        fun onAllMoviesReceived(movieResponse: List<MovieResponse>)
    }

    interface LoadPopularTvShowCallback {
        fun onAllTvShowsReceived(tvResponse: List<TvShowResponse>)
    }

    interface LoadMovieDetailCallback {
        fun onMovieDetailReceived(movieResponse: MovieResponse)
    }

    interface LoadTvShowDetailCallback {
        fun onTvShowDetailReceived(tvShowResponse: TvShowResponse)
    }
}