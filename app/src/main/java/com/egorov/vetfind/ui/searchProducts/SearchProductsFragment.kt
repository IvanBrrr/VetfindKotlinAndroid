package com.egorov.vetfind.ui.searchProducts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorov.vetfind.adapter.SearchProductsAdapter
import com.egorov.vetfind.databinding.FragmentSearchProductsBinding
import com.egorov.vetfind.util.NetworkResult
import com.egorov.vetfind.util.hideKeyboard
import com.egorov.vetfind.viewmodel.SharedViewModel
import com.egorov.vetfind.viewmodel.VetfindViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchProductsFragment : Fragment() {

    private var _binding: FragmentSearchProductsBinding? = null
    private val binding get() = _binding!!

    private val searchProductsAdapter: SearchProductsAdapter by lazy { SearchProductsAdapter() }
    private lateinit var vetfindViewModel: VetfindViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private var sortBy: String = "price"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vetfindViewModel = ViewModelProvider(this).get(VetfindViewModel::class.java)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        _binding = FragmentSearchProductsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel
        val root: View = binding.root

        setupRecyclerView()

        binding.searchField.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        vetfindViewModel.searchProductsResponse.observe(viewLifecycleOwner, { res ->
            when (res) {
                is NetworkResult.Success -> {
                    sharedViewModel.setResponse(res)
                    res.data?.let { searchProductsAdapter.setData(it) }
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
        binding.isOpenNow.setOnCheckedChangeListener { _, _ ->
            performSearch()
        }

        return root
    }

    private fun performSearch() {
        val shortName = binding.searchField.editText?.text.toString()
        val isOpenNow = binding.isOpenNow.isChecked
        vetfindViewModel.searchProduct(shortName, sortBy, "0", "0", isOpenNow)
    }

    private fun setupRecyclerView() {
        binding.searchProductsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchProductsRecyclerView.adapter = searchProductsAdapter
        binding.searchProductsRecyclerView.isNestedScrollingEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}