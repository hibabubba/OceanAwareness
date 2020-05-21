package com.example.prosjekt.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prosjekt.Dataclasses.SetImage
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.Repository.OceanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OceanActivityViewModel {
    lateinit var setImage: SetImage

    private val repository = OceanRepository()

    var liveData: MutableLiveData<Oceanforecast> =
        repository.liveDataForecast //MutableLiveData<RSSObject>()


    fun setCustomValue(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.fetchForecast(lat, long)
        }
    }

    fun getData(): LiveData<Oceanforecast> {
        return liveData
    }
    //fun set_dataImage( tell: Int , nr: Int , data: Oceanforecast?): SetImage {}
    //fun set_image_logic( setImage: SetImage): Int {}

}