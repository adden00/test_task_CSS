package com.example.testtaskcss.presentation.favourite_values

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.RateItem
import com.example.testtaskcss.R
import com.example.testtaskcss.databinding.FragmentFavourBinding
import com.example.testtaskcss.presentation.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavourFragment : Fragment() {
    private lateinit var binding: FragmentFavourBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: FavourRateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavourBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeData()
        observeLoading()
        setAdapter()
        setSpinner()
        setButtons()
    }

    private fun setButtons() {
        binding.btnSort.setOnClickListener {
            viewModel.showHideFilter()

        }

        binding.includedFilter.btnSort.setOnClickListener {
            val sortType = binding.includedFilter.spinner.selectedItem.toString()
            viewModel.sortFavour(sortType)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.filterIsShown.collect {
                binding.includedFilterLayout.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setSpinner() {
        val brandSpinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_types,
            R.layout.spinner_text_style
        )
        brandSpinnerAdapter.setDropDownViewResource(R.layout.spinner_text_style)
        binding.includedFilter.spinner.adapter = brandSpinnerAdapter
        binding.includedFilter.spinner.setPopupBackgroundResource(R.drawable.spinner_poup_bg)
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.favourRateList.collect {
                adapter.submitList(it)
                Log.d("MyLog", "submitted! $it")
            }
        }
    }

    private fun observeLoading() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.updateFavouriteRate()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect {
                binding.swipeToRefresh.isRefreshing = it
            }
        }
    }

    private fun setAdapter() {
        adapter = FavourRateAdapter(object : FavourRateAdapter.Listener {
            override fun onBtnClick(item: RateItem) {
                Snackbar.make(binding.root, "removed from favour", Snackbar.ANIMATION_MODE_SLIDE).show()
                viewModel.deleteRateFromFavour(item)
            }
        })
        binding.rcRates.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcRates.adapter = adapter
    }


}