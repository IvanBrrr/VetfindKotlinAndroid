package com.egorov.vetfind.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.egorov.vetfind.databinding.OrganizationCardBinding
import com.egorov.vetfind.model.CompanyProduct
import com.egorov.vetfind.util.MainDiffUtil

class OrganizationsAdapter : RecyclerView.Adapter<OrganizationsAdapter.OrganizationsViewHolder>() {

    private val data: ArrayList<CompanyProduct> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationsViewHolder {
        return OrganizationsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrganizationsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(organizations: List<CompanyProduct>) {
        val organizationsDiffUtil = MainDiffUtil(data, organizations)
        val organizationsDiffResult = DiffUtil.calculateDiff(organizationsDiffUtil)
        this.data.clear()
        this.data.addAll(organizations)
        organizationsDiffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

    class OrganizationsViewHolder(private val binding: OrganizationCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(organization: CompanyProduct) {
            binding.companyProduct = organization
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OrganizationsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OrganizationCardBinding.inflate(layoutInflater, parent, false)
                return OrganizationsViewHolder(binding)
            }
        }
    }
}