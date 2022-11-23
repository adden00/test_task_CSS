package com.example.testtaskcss.presentation.search_values

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
import com.example.testtaskcss.databinding.FragmentSearchBinding
import com.example.testtaskcss.presentation.MainViewModel
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: SearchRateAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe()
        setAdapter()
        setButton()

    }

    private fun setButton() {
        binding.btnLoad.setOnClickListener {
            viewModel.loadRate(binding.edCurrency.text.toString())
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.rateList.collect {
                adapter.submitList(it)
                Log.d("MyLog", "submitted! $it")
            }
        }
    }

    private fun setAdapter() {
        adapter = SearchRateAdapter(object : SearchRateAdapter.Listener {
            override fun onBtnClick(item: RateItem) {
                Snackbar.make(binding.root, "added to favour", Snackbar.LENGTH_LONG).show()
                viewModel.insetRateToFavour(item)
            }

        })
        binding.rcRates.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcRates.adapter = adapter
    }

}