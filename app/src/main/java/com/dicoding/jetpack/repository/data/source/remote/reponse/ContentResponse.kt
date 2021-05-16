package com.dicoding.jetpack.repository.data.source.remote.reponse

data class ContentResponse <T> (
    var results: List<T>
)