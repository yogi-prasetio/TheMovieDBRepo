package com.dicoding.jetpack.repository.ui.movies

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.jetpack.repository.R
import com.dicoding.jetpack.repository.databinding.ActivityDetailBinding
import com.dicoding.jetpack.repository.viewmodel.ViewModelFactory
import com.dicoding.jetpack.repository.viewmodel.detail.DetailMovieViewModel

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_MOVIES = "extra_movies"
        const val BASE_URL = "https://image.tmdb.org/t/p/original"
    }

    @SuppressLint("WrongConstant", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailMovieViewModel::class.java)
        val id = intent.getIntExtra(EXTRA_MOVIES, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvItemDescription.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        showLoading(true)
        viewModel.getMovieDetail(id).observe(this, {
            val hour =  it.runtime!!.div(60)
            val minutes = it.runtime!!.rem(60)
            if(it != null){
                binding.apply {
                    tvItemTitle.text = it.title
                    tvItemRelease.text = it.release_date
                    tvItemRating.text = it.vote_average
                    tvItemDuration.text = "${hour}h ${minutes}m"
                    tvItemDescription.text = it.overview

                    Glide.with(this@DetailMovieActivity)
                            .load(BASE_URL + it.poster_path)
                            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                            .into(imgPoster)
                }
                showLoading(false)
            }
        })

    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}