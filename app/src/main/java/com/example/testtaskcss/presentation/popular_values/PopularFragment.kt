package com.example.testtaskcss.presentation.popular_values

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.RateItem
import com.example.testtaskcss.databinding.FragmentPopularBinding
import com.example.testtaskcss.presentation.MainViewModel
import com.google.android.material.snackbar.Snackbar

class PopularFragment: Fragment() {
    private lateinit var binding: FragmentPopularBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: PopularRateAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observePopular()
        observeRefresh()
        setAdapter()

    }

    private fun observeRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.loadPopularRate()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect {
                binding.swipeToRefresh.isRefreshing = it
            }
        }
    }

    private fun observePopular() {



        lifecycleScope.launchWhenStarted {
            viewModel.popularRateList.collect {
                adapter.submitList(it)
                Log.d("MyLog", "submitted! $it")
            }
        }
    }

    private fun setAdapter() {
        adapter = PopularRateAdapter(object : PopularRateAdapter.Listener {
            override fun onBtnClick(item: RateItem) {
                Snackbar.make(binding.root, "added to favour", Snackbar.LENGTH_LONG).show()
                viewModel.insetRateToFavour(item)
            }

        })
        binding.rcRates.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcRates.adapter = adapter
    }

}