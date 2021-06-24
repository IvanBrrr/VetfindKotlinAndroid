package com.egorov.vetfind.ui.organizations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorov.vetfind.R
import com.egorov.vetfind.adapter.OrganizationsAdapter
import com.egorov.vetfind.databinding.FragmentOrganizationsBinding
import com.egorov.vetfind.util.NetworkResult
import com.egorov.vetfind.viewmodel.SharedViewModel
import com.egorov.vetfind.viewmodel.VetfindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationsFragment : Fragment() {

    private var _binding: FragmentOrganizationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var vetfindViewModel: VetfindViewModel
    private val organizationsAdapter: OrganizationsAdapter by lazy { OrganizationsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        vetfindViewModel = ViewModelProvider(this).get(VetfindViewModel::class.java)

        _binding = FragmentOrganizationsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel
        val root: View = binding.root

        setupRecyclerView()

        vetfindViewModel.organizationsResponse.observe(viewLifecycleOwner, { res ->
            when (res) {
                is NetworkResult.Success -> {
                    sharedViewModel.setResponse(res)
                    res.data?.let { organizationsAdapter.setData(it) }
                }
                is NetworkResult.Loading -> {
                    sharedViewModel.setResponse(res)
                }
                is NetworkResult.Error -> {
                    sharedViewModel.setResponse(res)
                }
            }
        })

        vetfindViewModel.fetchOrganizations()

        return root
    }

    private fun setupRecyclerView() {
        binding.organizationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.organizationsRecyclerView.adapter = organizationsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}