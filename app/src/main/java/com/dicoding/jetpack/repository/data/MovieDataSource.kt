package com.dicoding.jetpack.repository.data

import androidx.lifecycle.LiveData
import com.dicoding.submission.movies.model.MoviesEntity
import com.dicoding.submission.movies.model.TvshowEntity

interface MovieDataSource {
    fun getPopularMovie(): LiveData<List<MoviesEntity>>

    fun getPopularTvShow(): LiveData<List<TvshowEntity>>

    fun getMovieDetail(movieId: Int): LiveData<MoviesEntity>

    fun getTvShowDetail(tvShowId: Int): LiveData<TvshowEntity>
}