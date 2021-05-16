package com.dicoding.jetpack.repository.viewmodel.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.jetpack.repository.utils.DataDummy
import com.dicoding.submission.movies.model.MoviesEntity
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id

    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<MoviesEntity>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
    }

    @Test
    fun getMovieDetail() {
        val movieDummy = MutableLiveData<MoviesEntity>()
        movieDummy.value = dummyMovie

        Mockito.`when`(movieRepository.getMovieDetail(movieId)).thenReturn(movieDummy)

        val data = viewModel.getMovieDetail(movieId).value as MoviesEntity

        Assert.assertNotNull(data)
        assertEquals(dummyMovie.id, data.id)
        assertEquals(dummyMovie.poster_path, data.poster_path)
        assertEquals(dummyMovie.title, data.title)
        assertEquals(dummyMovie.release_date, data.release_date)
        assertEquals(dummyMovie.runtime, data.runtime)
        assertEquals(dummyMovie.vote_average, data.vote_average)
        assertEquals(dummyMovie.overview, data.overview)

        viewModel.getMovieDetail(movieId).observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}