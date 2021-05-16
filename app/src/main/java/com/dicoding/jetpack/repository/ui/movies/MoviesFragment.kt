package com.dicoding.jetpack.repository.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.jetpack.repository.databinding.FragmentMoviesBinding
import com.dicoding.jetpack.repository.viewmodel.ViewModelFactory
import com.dicoding.submission.movies.viewmodel.MoviesViewModel

class MoviesFragment : Fragment() {
    private var frgmntmoviesbinding: FragmentMoviesBinding? = null
    private val binding get() = frgmntmoviesbinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        frgmntmoviesbinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return frgmntmoviesbinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val viewModel = ViewModelProvider(this, ViewModelFactory.getInstance())[MoviesViewModel::class.java]
            val moviesAdapter = MoviesAdapter()
            showLoading(true)

            viewModel.getListPopularMovies().observe(viewLifecycleOwner, {
                if (it!=null) {
                    moviesAdapter.setMovies(it)
                    showLoading(false)
                }
            })

            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        frgmntmoviesbinding = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}