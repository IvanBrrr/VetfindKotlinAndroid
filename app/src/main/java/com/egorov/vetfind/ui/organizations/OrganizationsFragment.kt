package com.egorov.vetfind.ui.organizations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egorov.vetfind.R
import com.egorov.vetfind.databinding.FragmentOrganizationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationsFragment : Fragment() {

    private var _binding: FragmentOrganizationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrganizationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}