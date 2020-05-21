package com.example.prosjekt.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prosjekt.Repository.RSSRepository
import com.example.prosjekt.ViewModel.MenuActivityViewModel

@Suppress("UNCHECKED_CAST")
class MenuActivityViewModelFactory constructor(private val repository : RSSRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>)
            : T = MenuActivityViewModel(repository) as T
}