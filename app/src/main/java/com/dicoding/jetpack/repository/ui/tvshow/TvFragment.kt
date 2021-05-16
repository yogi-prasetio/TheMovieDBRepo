package com.dicoding.submission.movies.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.jetpack.repository.databinding.FragmentTvshowBinding
import com.dicoding.jetpack.repository.viewmodel.ViewModelFactory
import com.dicoding.submission.movies.view.adapter.TvAdapter
import com.dicoding.submission.movies.viewmodel.TvViewModel

class TvFragment : Fragment() {
    private var frgmntbinding: FragmentTvshowBinding? = null
    private val binding get() = frgmntbinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        frgmntbinding = FragmentTvshowBinding.inflate(layoutInflater, container, false)
        return frgmntbinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val viewModel = ViewModelProvider(this, ViewModelFactory.getInstance()).get(TvViewModel::class.java)
            val tvAdapter = TvAdapter()
            showLoading(true)

            viewModel.getListPopularTvShow().observe(viewLifecycleOwner, {
                if (it!=null) {
                    tvAdapter.setTvShow(it)
                    showLoading(false)
                }
            })

            binding.apply {
                rvTvShow.layoutManager = LinearLayoutManager(context)
                rvTvShow.setHasFixedSize(true)
                rvTvShow.adapter = tvAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        frgmntbinding = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}