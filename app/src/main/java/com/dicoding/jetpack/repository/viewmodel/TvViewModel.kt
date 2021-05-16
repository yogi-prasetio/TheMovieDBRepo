package com.dicoding.submission.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.submission.movies.model.TvshowEntity

class TvViewModel(private val mRepository: MovieRepository): ViewModel() {

    fun getListPopularTvShow(): LiveData<List<TvshowEntity>> = mRepository.getPopularTvShow()

}