package com.dicoding.jetpack.repository.data.source.remote.reponse

data class TvShowResponse(
        var id: Int = 0,
        var poster_path: String? = null,
        var name: String? = null,
        var first_air_date: String? = null,
        var episode_run_time: List<Int>? = null,
        var vote_average: String? = null,
        var overview: String? = null
)
