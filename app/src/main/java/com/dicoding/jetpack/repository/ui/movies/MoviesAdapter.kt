package com.dicoding.jetpack.repository.ui.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.jetpack.repository.R
import com.dicoding.jetpack.repository.databinding.ItemsMoviesBinding
import com.dicoding.submission.movies.model.MoviesEntity

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private var listMovies = ArrayList<MoviesEntity>()

    companion object {
        const val BASE_URL = "https://image.tmdb.org/t/p/original"
    }

    fun setMovies(movies: List<MoviesEntity>?) {
        if (movies == null) return
        this.listMovies.clear()
        this.listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsMoviesBinding = ItemsMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsMoviesBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val data = listMovies[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listMovies.size

    class MoviesViewHolder(private val binding: ItemsMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("WrongConstant")
        fun bind(movies: MoviesEntity) {
            with(binding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tvItemDescription.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
                }
                tvItemTitle.text = movies.title
                tvItemDescription.text = movies.overview

                Glide.with(itemView.context)
                    .load(BASE_URL + movies.poster_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIES, movies.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}