package com.dicoding.jetpack.repository.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.jetpack.repository.utils.DataDummy
import com.dicoding.submission.movies.model.TvshowEntity
import com.dicoding.submission.movies.viewmodel.TvViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {
    private lateinit var viewModel : TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<TvshowEntity>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(movieRepository)
    }

    @Test
    fun testGetListPopularTvShow() {
        val dummyMovie = DataDummy.generateDummyTvshow()
        val tvShow = MutableLiveData<List<TvshowEntity>>()
        tvShow.value = dummyMovie

        Mockito.`when`(movieRepository.getPopularTvShow()).thenReturn(tvShow)
        val tvShowEntities = viewModel.getListPopularTvShow().value
        Mockito.verify(movieRepository).getPopularTvShow()
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(10, tvShowEntities?.size)

        viewModel.getListPopularTvShow().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
}