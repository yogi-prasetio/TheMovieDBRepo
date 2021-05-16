package com.dicoding.jetpack.repository.data.api

import com.dicoding.jetpack.repository.BuildConfig
import com.dicoding.jetpack.repository.data.source.remote.reponse.ContentResponse
import com.dicoding.jetpack.repository.data.source.remote.reponse.MovieResponse
import com.dicoding.jetpack.repository.data.source.remote.reponse.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
            @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): Call<ContentResponse<MovieResponse>>

    @GET("tv/popular")
    fun getPopularTv(
            @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): Call<ContentResponse<TvShowResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
            @Path("movie_id") movie_id: Int,
            @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): Call<MovieResponse>

    @GET("tv/{tv_id}")
    fun getTvDetail(
            @Path("tv_id") tv_id: Int,
            @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): Call<TvShowResponse>
}