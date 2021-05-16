package com.dicoding.jetpack.repository.viewmodel.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.jetpack.repository.data.MovieRepository
import com.dicoding.jetpack.repository.utils.DataDummy
import com.dicoding.submission.movies.model.TvshowEntity
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {
    private val dummyTvShow = DataDummy.generateDummyTvshow()[0]
    private val tvShowId = dummyTvShow.id

    private lateinit var viewModel: DetailTvShowViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<TvshowEntity>

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(movieRepository)
    }

    @Test
    fun getTvShowDetail() {
        val tvShowDummy = MutableLiveData<TvshowEntity>()
        tvShowDummy.value = dummyTvShow

        Mockito.`when`(movieRepository.getTvShowDetail(tvShowId)).thenReturn(tvShowDummy)

        val data = viewModel.getTvShowDetail(tvShowId).value as TvshowEntity

        Assert.assertNotNull(data)
        Assert.assertEquals(dummyTvShow.id, data.id)
        Assert.assertEquals(dummyTvShow.poster_path, data.poster_path)
        Assert.assertEquals(dummyTvShow.name, data.name)
        Assert.assertEquals(dummyTvShow.first_air_date, data.first_air_date)
        Assert.assertEquals(dummyTvShow.episode_run_time, data.episode_run_time)
        Assert.assertEquals(dummyTvShow.vote_average, data.vote_average)
        Assert.assertEquals(dummyTvShow.overview, data.overview)

        viewModel.getTvShowDetail(tvShowId).observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}