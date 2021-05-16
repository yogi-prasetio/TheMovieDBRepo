package com.dicoding.jetpack.repository.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.submission.movies.model.TvshowEntity

class DetailTvShowViewModel(private val repo: MovieRepository): ViewModel() {

    fun getTvShowDetail(tvShowId: Int): LiveData<TvshowEntity> = repo.getTvShowDetail(tvShowId)

}