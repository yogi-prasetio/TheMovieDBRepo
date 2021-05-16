package com.dicoding.jetpack.repository.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.submission.movies.model.MoviesEntity

class DetailMovieViewModel(private val repo: MovieRepository): ViewModel() {
    fun getMovieDetail(movieId: Int): LiveData<MoviesEntity> = repo.getMovieDetail(movieId)
}