package com.egorov.vetfind.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.egorov.vetfind.databinding.OrganizationCardBinding
import com.egorov.vetfind.databinding.SearchProductCardBinding
import com.egorov.vetfind.model.CompanyProduct
import com.egorov.vetfind.util.MainDiffUtil

class SearchProductsAdapter : RecyclerView.Adapter<SearchProductsAdapter.SearchProductsViewHolder>() {

    private val data: ArrayList<CompanyProduct> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductsViewHolder {
        return SearchProductsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchProductsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(products: List<CompanyProduct>) {
        val productsDiffUtil = MainDiffUtil(data, products)
        val productsDiffResult = DiffUtil.calculateDiff(productsDiffUtil)
        this.data.clear()
        this.data.addAll(products)
        productsDiffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

    class SearchProductsViewHolder(private val binding: SearchProductCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CompanyProduct) {
            binding.companyProduct = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchProductsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchProductCardBinding.inflate(layoutInflater, parent, false)
                return SearchProductsViewHolder(binding)
            }
        }
    }
}