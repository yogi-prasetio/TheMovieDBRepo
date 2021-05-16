package com.dicoding.jetpack.repository.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.jetpack.repository.utils.DataDummy
import com.dicoding.submission.movies.model.MoviesEntity
import com.dicoding.submission.movies.viewmodel.MoviesViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel : MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<MoviesEntity>>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(movieRepository)
    }

    @Test
    fun testGetListPopularMovies() {
        val dummyMovie = DataDummy.generateDummyMovies()
        val movies = MutableLiveData<List<MoviesEntity>>()
        movies.value = dummyMovie

        `when`(movieRepository.getPopularMovie()).thenReturn(movies)
        val movieEntities = viewModel.getListPopularMovies().value
        Mockito.verify(movieRepository).getPopularMovie()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)

        viewModel.getListPopularMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
}