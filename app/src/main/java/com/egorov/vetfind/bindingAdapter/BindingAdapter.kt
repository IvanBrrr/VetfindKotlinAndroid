package com.egorov.vetfind.bindingAdapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.egorov.vetfind.util.NetworkResult

class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("android:showLoading")
        fun showLoading(view: View, res: NetworkResult<*>?) {
            view.isVisible = res is NetworkResult.Loading
        }

        @JvmStatic
        @BindingAdapter("android:showDataByResponse")
        fun showDataByResponse(view: View, res: NetworkResult<*>?) {
            view.isVisible = res is NetworkResult.Success && res.data != null
        }

        @JvmStatic
        @BindingAdapter("android:showError")
        fun showError(view: View, res: NetworkResult<*>?) {
            view.isVisible = res is NetworkResult.Error
        }

        @JvmStatic
        @BindingAdapter("android:noData")
        fun noData(view: View, res: NetworkResult<*>?) {
            view.isVisible = res is NetworkResult.Success && res.data == null
        }
    }
}