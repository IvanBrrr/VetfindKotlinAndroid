package com.egorov.vetfind.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.egorov.vetfind.databinding.ProductCardBinding
import com.egorov.vetfind.model.CompanyProduct
import com.egorov.vetfind.util.MainDiffUtil

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val data: ArrayList<CompanyProduct> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(products: List<CompanyProduct>) {
        val productsDiffUtil = MainDiffUtil(data, products)
        val productsDiffResult = DiffUtil.calculateDiff(productsDiffUtil)
        this.data.clear()
        this.data.addAll(products)
        productsDiffResult.dispatchUpdatesTo(this)
    }

    class ProductsViewHolder(private val binding: ProductCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CompanyProduct) {
            binding.companyProduct = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductCardBinding.inflate(layoutInflater, parent, false)
                return ProductsViewHolder(binding)
            }
        }
    }
}