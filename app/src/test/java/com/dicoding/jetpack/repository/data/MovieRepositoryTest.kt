package com.dicoding.jetpack.repository.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.jetpack.repository.data.source.remote.RemoteDataSource
import com.dicoding.jetpack.repository.utils.DataDummy
import com.dicoding.jetpack.repository.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val movieRepository = FakeMovieRepository(remote)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val movieId = movieResponses[2].id
    private val tvShowResponses = DataDummy.generateRemoteDummyTvshow()
    private val tvShowId = tvShowResponses[2].id
    private val movieResponse = DataDummy.generateRemoteDummyMovies()[2]
    private val tvShowResponse =  DataDummy.generateRemoteDummyTvshow()[2]

    @Test
    fun testGetPopularMovie() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[0] as RemoteDataSource.LoadPopularMoviesCallback).onAllMoviesReceived(movieResponses)
                null
            }.`when`(remote).getPopularMovie(any())
        }
        
        val data = LiveDataTestUtil.getValue(movieRepository.getPopularMovie())

        runBlocking {
            verify(remote).getPopularMovie(any())
        }

        assertNotNull(data)
        assertEquals(movieResponses.size.toLong(), data.size.toLong())
    }

    @Test
    fun testGetPopularTvShow() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[0] as RemoteDataSource.LoadPopularTvShowCallback).onAllTvShowsReceived(tvShowResponses)
                null
            }.`when`(remote).getPopularTvShow(any())
        }

        val data = LiveDataTestUtil.getValue(movieRepository.getPopularTvShow())

        runBlocking {
            verify(remote).getPopularTvShow(any())
        }

        assertNotNull(data)
        assertEquals(tvShowResponses.size.toLong(), data.size.toLong())
    }

    @Test
    fun testGetMovieDetail() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadMovieDetailCallback).onMovieDetailReceived(movieResponse)
                null
            }.`when` (remote).getMovieDetail(eq(movieId), any())
        }

        val data = LiveDataTestUtil.getValue(movieRepository.getMovieDetail(movieId))

        runBlocking {
            verify(remote).getMovieDetail(eq(movieId), any())
        }

        assertNotNull(data)
        assertEquals(movieResponse.id, data.id)
    }

    @Test
    fun testGetTvShowDetail() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadTvShowDetailCallback).onTvShowDetailReceived(tvShowResponse)
                null
            }.`when` (remote).getTvShowDetail(eq(tvShowId), any())
        }

        val data = LiveDataTestUtil.getValue(movieRepository.getTvShowDetail(tvShowId))

        runBlocking {
            verify(remote).getTvShowDetail(eq(tvShowId), any())
        }

        assertNotNull(data)
        assertEquals(tvShowResponse.id, data.id)
    }
}