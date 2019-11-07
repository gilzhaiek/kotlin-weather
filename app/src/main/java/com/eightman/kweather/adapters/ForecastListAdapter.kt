package com.eightman.kweather.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eightman.kweather.databinding.ForecastItemBinding
import com.eightman.kweather.network.City
import com.eightman.kweather.network.Forecast
import com.eightman.kweather.utils.getUiDate
import kotlinx.android.synthetic.main.forecast_item.*

class ForecastListAdapter() :
    ListAdapter<Forecast, ForecastListAdapter.ForecastListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
            (oldItem == newItem)

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean =
            (oldItem == newItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder =
        ForecastListViewHolder.from(parent)

    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ForecastListViewHolder(private var binding: ForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: Forecast) {
            binding.forecast = forecast
            binding.clickListener =
                ForecastClickListener {
                    binding.forecastExtended.visibility =
                        if (binding.forecastExtended.visibility == View.GONE) View.VISIBLE
                        else View.GONE
                }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ForecastListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ForecastItemBinding.inflate(layoutInflater, parent, false)
                return ForecastListViewHolder(binding)
            }
        }
    }
}

class ForecastClickListener(val clickListener: (forecast: Forecast) -> Unit) {
    fun onClick(forecast: Forecast) = clickListener(forecast)
}

