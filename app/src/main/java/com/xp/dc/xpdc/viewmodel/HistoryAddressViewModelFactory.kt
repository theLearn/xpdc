package com.xp.dc.xpdc.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.hongcheng.common.base.CommonUI

class HistoryAddressViewModelFactory(private val ui: CommonUI) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryAddressViewModel(ui) as T
    }
}