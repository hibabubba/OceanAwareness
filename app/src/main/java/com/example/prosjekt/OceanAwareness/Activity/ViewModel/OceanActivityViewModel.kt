package com.example.prosjekt.OceanAwareness.Activity.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.OceanAwareness.Activity.Repository.OceanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OceanActivityViewModel {

    private val repository = OceanRepository()
    private var liveData: MutableLiveData<Oceanforecast> = repository.liveDataForecast


    fun setCustomValue(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.fetchForecast(lat, long)
        }
    }

    fun getData(): LiveData<Oceanforecast> {
        return liveData
    }

}