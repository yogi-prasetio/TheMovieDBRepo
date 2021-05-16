package com.dicoding.submission.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.submission.movies.model.MoviesEntity

class MoviesViewModel(private val mRepository: MovieRepository) : ViewModel() {

    fun getListPopularMovies(): LiveData<List<MoviesEntity>> = mRepository.getPopularMovie()
}