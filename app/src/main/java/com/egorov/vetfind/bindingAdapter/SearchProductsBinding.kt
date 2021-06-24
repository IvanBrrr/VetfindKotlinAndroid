package com.egorov.vetfind.bindingAdapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.egorov.vetfind.ui.searchProducts.SearchProductsFragmentDirections

class SearchProductsBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("android:sendCompanyIdToOrganization")
        fun sendCompanyIdToOrganization(view: View, companyId: Long) {
            view.setOnClickListener {
                val action = SearchProductsFragmentDirections.actionSearchProductsFragmentToOrganizationActivity(companyId)
                it.findNavController().navigate(action)
            }
        }
    }
}