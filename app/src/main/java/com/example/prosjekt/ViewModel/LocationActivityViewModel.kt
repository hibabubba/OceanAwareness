package com.example.prosjekt.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocationActivityViewModel () : ViewModel() {

    private val repository = LocationRepository()

    var liveData: MutableLiveData<Locationforecast> = repository.liveDataForecast //MutableLiveData<RSSObject>()



    fun setCustomValue(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.fetchForecast(lat, long)
        }
    }

    fun getData() : LiveData<Locationforecast> {
        return liveData
    }
}