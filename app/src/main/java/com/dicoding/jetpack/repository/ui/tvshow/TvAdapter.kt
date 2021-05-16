package com.dicoding.submission.movies.view.adapter

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
import com.dicoding.jetpack.repository.databinding.ItemsTvShowBinding
import com.dicoding.jetpack.repository.ui.tvshow.DetailTvShowActivity
import com.dicoding.submission.movies.model.TvshowEntity

class TvAdapter: RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    private var listTv = ArrayList<TvshowEntity>()

    companion object {
        const val BASE_URL = "https://image.tmdb.org/t/p/original"
    }

    fun setTvShow(tvShow: List<TvshowEntity>?) {
        if (tvShow == null) return
        this.listTv.clear()
        this.listTv.addAll(tvShow)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsTvBinding = ItemsTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsTvBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val Tv = listTv[position]
        holder.bind(Tv)
    }

    override fun getItemCount(): Int = listTv.size

    class TvViewHolder(private val binding: ItemsTvShowBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("WrongConstant")
        fun bind(data: TvshowEntity) {
            with(binding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tvItemDescription.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
                }
                tvItemTitle.text = data.name
                tvItemRelease.text = data.first_air_date
                tvItemDescription.text = data.overview
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvShowActivity::class.java)
                    intent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, data.id)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                        .load(BASE_URL+data.poster_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                        .into(imgPoster)
            }
        }
    }
}
