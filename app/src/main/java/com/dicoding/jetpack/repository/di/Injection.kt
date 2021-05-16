package com.dicoding.jetpack.repository.di

import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.jetpack.repository.data.source.remote.RemoteDataSource

object Injection {
    fun provideMovieRepository(): MovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return MovieRepository.getInstance(remoteDataSource)
    }
}