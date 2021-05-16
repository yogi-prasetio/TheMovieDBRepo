package com.dicoding.jetpack.repository.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.jetpack.repository.di.Injection
import com.dicoding.jetpack.repository.viewmodel.detail.DetailMovieViewModel
import com.dicoding.jetpack.repository.viewmodel.detail.DetailTvShowViewModel
import com.dicoding.submission.movies.viewmodel.MoviesViewModel
import com.dicoding.submission.movies.viewmodel.TvViewModel

class ViewModelFactory private constructor(private val mRepository: MovieRepository): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideMovieRepository())
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> {
                DetailTvShowViewModel(mRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}