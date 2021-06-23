package com.egorov.vetfind.ui.organizations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorov.vetfind.adapter.OrganizationsAdapter
import com.egorov.vetfind.databinding.FragmentOrganizationsBinding
import com.egorov.vetfind.util.NetworkResult
import com.egorov.vetfind.util.hideKeyboard
import com.egorov.vetfind.util.hideKeyboardFrom
import com.egorov.vetfind.viewmodel.SharedViewModel
import com.egorov.vetfind.viewmodel.VetfindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationsFragment : Fragment() {

    private var _binding: FragmentOrganizationsBinding? = null
    private val binding get() = _binding!!

    private val organizationsAdapter: OrganizationsAdapter by lazy { OrganizationsAdapter() }
    private lateinit var vetfindViewModel: VetfindViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private var sortBy: String = "price"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vetfindViewModel = ViewModelProvider(this).get(VetfindViewModel::class.java)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

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
                is NetworkResult.Loading -> sharedViewModel.setResponse(res)
                is NetworkResult.Error -> sharedViewModel.setResponse(res)
            }
        })

        binding.searchField.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(requireActivity())
                performSearch()
            }
            false
        }
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.button1.id -> {
                        sortBy = "price"
                        performSearch()
                    }
                    binding.button2.id -> {
                        sortBy = "distance"
                        performSearch()
                    }
                }
            }
        }

        return root
    }

    private fun performSearch() {
        val shortName = binding.searchField.editText?.text.toString()
        vetfindViewModel.fetchOrganizations(shortName, sortBy, "0", "0")
    }

    private fun setupRecyclerView() {
        binding.organizationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.organizationsRecyclerView.adapter = organizationsAdapter
        binding.organizationsRecyclerView.isNestedScrollingEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}