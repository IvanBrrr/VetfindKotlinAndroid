package com.egorov.vetfind.ui.organization

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.egorov.vetfind.adapter.ProductsAdapter
import com.egorov.vetfind.databinding.ActivityOrganizationBinding
import com.egorov.vetfind.ui.MapActivity
import com.egorov.vetfind.util.NetworkResult
import com.egorov.vetfind.viewmodel.SharedViewModel
import com.egorov.vetfind.viewmodel.VetfindViewModel
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationActivity : AppCompatActivity() {

    private val args: OrganizationActivityArgs by navArgs()

    private lateinit var binding: ActivityOrganizationBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private val vetfindViewModel: VetfindViewModel by viewModels()
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter() }
    private var latitude: String? = null
    private var longitude: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrganizationBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        setupRecyclerView()
        binding.mapBtn.setOnClickListener {
            if (latitude != null && longitude != null) {
                val intent = Intent(this, MapActivity::class.java)
                intent.putExtra("latitude", latitude)
                intent.putExtra("longitude", longitude)
                startActivity(intent)
            }
        }

        vetfindViewModel.organizationResponse.observe(this, { res ->
            when (res) {
                is NetworkResult.Success -> {
                    res.data?.let {
                        val geo = it[0].company.geo.split(",")
                        latitude = geo[0]
                        longitude = geo[1]
                    }
                    sharedViewModel.setResponse(res)
                    res.data?.let { binding.companyProduct = it[0] }
                    res.data?.let { productsAdapter.setData(it) }
                }
                is NetworkResult.Loading -> sharedViewModel.setResponse(res)
                is NetworkResult.Error -> sharedViewModel.setResponse(res)
            }
        })

        vetfindViewModel.fetchOrganization(args.companyId)
    }

    private fun setupRecyclerView() {
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView.adapter = productsAdapter
        binding.productsRecyclerView.isNestedScrollingEnabled = false
    }
}