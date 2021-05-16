package com.dicoding.jetpack.repository.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.jetpack.repository.data.source.remote.RemoteDataSource
import com.dicoding.jetpack.repository.data.source.remote.reponse.MovieResponse
import com.dicoding.jetpack.repository.data.source.remote.reponse.TvShowResponse
import com.dicoding.submission.movies.model.MoviesEntity
import com.dicoding.submission.movies.model.TvshowEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MovieRepository private constructor(private val remoteDataSource: RemoteDataSource) : MovieDataSource {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MovieRepository =
                instance ?: synchronized(this) {
                    MovieRepository(remoteData).apply { instance = this }
                }
    }

    override fun getPopularMovie(): LiveData<List<MoviesEntity>> {
        val listMovie = MutableLiveData<List<MoviesEntity>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getPopularMovie(object : RemoteDataSource.LoadPopularMoviesCallback{
                override fun onAllMoviesReceived(movieResponse: List<MovieResponse>) {
                    val movieList = ArrayList<MoviesEntity>()
                    for (response in movieResponse){
                        val movie = MoviesEntity(
                                response.id,
                                response.poster_path,
                                response.title,
                                response.release_date,
                                response.runtime,
                                response.vote_average,
                                response.overview
                        )
                        movieList.add(movie)
                    }
                    listMovie.postValue(movieList)
                }
            })
        }

        return listMovie
    }

    override fun getPopularTvShow(): LiveData<List<TvshowEntity>> {
        val listTvShow = MutableLiveData<List<TvshowEntity>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getPopularTvShow(object : RemoteDataSource.LoadPopularTvShowCallback {
                override fun onAllTvShowsReceived(tvResponse: List<TvShowResponse>) {
                    val tvList = ArrayList<TvshowEntity>()
                    for (response in tvResponse) {
                        val tvshow = TvshowEntity(
                                response.id,
                                response.poster_path,
                                response.name,
                                response.first_air_date,
                                response.episode_run_time,
                                response.vote_average,
                                response.overview
                        )
                        tvList.add(tvshow)
                    }
                    listTvShow.postValue(tvList)
                }
            })
        }

        return listTvShow
    }

    override fun getMovieDetail(movieId: Int): LiveData<MoviesEntity> {
        val movieResult = MutableLiveData<MoviesEntity>()
        CoroutineScope(IO).launch {
            remoteDataSource.getMovieDetail(movieId, object : RemoteDataSource.LoadMovieDetailCallback{
                override fun onMovieDetailReceived(movieResponse: MovieResponse) {
                    val movie = MoviesEntity(
                            movieResponse.id,
                            movieResponse.poster_path,
                            movieResponse.title,
                            movieResponse.release_date,
                            movieResponse.runtime,
                            movieResponse.vote_average,
                            movieResponse.overview
                    )

                    movieResult.postValue(movie)
                }
            })
        }
        return movieResult
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<TvshowEntity> {
        val tvResult = MutableLiveData<TvshowEntity>()
        CoroutineScope(IO).launch {
            remoteDataSource.getTvShowDetail(tvShowId, object: RemoteDataSource.LoadTvShowDetailCallback {
                override fun onTvShowDetailReceived(tvShowResponse: TvShowResponse) {
                    val tvshow = TvshowEntity(
                            tvShowResponse.id,
                            tvShowResponse.poster_path,
                            tvShowResponse.name,
                            tvShowResponse.first_air_date,
                            tvShowResponse.episode_run_time,
                            tvShowResponse.vote_average,
                            tvShowResponse.overview
                    )
                    tvResult.postValue(tvshow)
                }
            })
        }
        return tvResult
    }
}