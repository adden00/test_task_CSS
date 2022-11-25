package com.example.testtaskcss.presentation.search_values

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.RateItem
import com.example.testtaskcss.R
import com.example.testtaskcss.databinding.ExchangeRatePopularItemBinding

class SearchRateAdapter(private val listener: Listener) :
    ListAdapter<RateItem, SearchRateAdapter.ItemHolder>(object : DiffUtil.ItemCallback<RateItem>() {
        override fun areItemsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
            return oldItem == newItem
        }

    }) {
    class ItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun setData(item: RateItem, listener: Listener) {
            val binding = ExchangeRatePopularItemBinding.bind(view)
            binding.tvCurrencyName.text = item.name
            binding.tvCurrencyRate.text = item.rate.toString()
            binding.btnLoad.setOnClickListener {
                listener.onBtnClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.exchange_rate_popular_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    interface Listener {
        fun onBtnClick(item: RateItem)
    }
}