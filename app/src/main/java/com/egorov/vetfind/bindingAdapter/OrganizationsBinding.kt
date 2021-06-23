package com.egorov.vetfind.bindingAdapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.egorov.vetfind.ui.organizations.OrganizationsFragmentDirections

class OrganizationsBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("android:sendCompanyIdToOrganization")
        fun sendCompanyIdToOrganization(view: View, companyId: Long) {
            view.setOnClickListener {
                val action = OrganizationsFragmentDirections.actionOrganizationsFragmentToOrganizationActivity(companyId)
                it.findNavController().navigate(action)
            }
        }
    }
}