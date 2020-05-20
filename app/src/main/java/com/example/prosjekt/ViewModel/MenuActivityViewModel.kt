package com.example.prosjekt.ViewModel

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.example.prosjekt.MenuActivity
import com.example.prosjekt.OceanActivity
import com.example.prosjekt.RSS.RSSObject
import com.example.prosjekt.Repository.Repository
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.AccessController.getContext


class MenuActivityViewModel (private val repository: Repository) : ViewModel() {

    var liveData : MutableLiveData<RSSObject> = repository.liveDataRSS //MutableLiveData<RSSObject>()


    fun setCustomValue(lat : Double, long : Double) {

        GlobalScope.launch(Dispatchers.IO) {
            repository.fetchRSSFeed(lat, long)
        }
    }

    fun getData() : LiveData<RSSObject> {
        return liveData
    }

/*
    val repositoryResult = Transformations.switchMap(repository.liveDataRSS) {repository ->
        repository.getRSSFeed(lat, long)
    }



    var liveDataMerger = MediatorLiveData<RSSObject>().addSource(liveData) {
        liveData.value = block.invoke(this.value, liveData.value)
    }





    liveDataMerger.addSource(repository.liveDataRSS, value -> liveDataMerger.setValue(value))

    fun getData(long : Double, lat : Double) : LiveData<RSSObject> {
        return liveData
        //repository.getRSSFeed(lat, long)
    }





    fun getRSSFeed(lat : Double, long : Double) {
        val liveDataMerger: MediatorLiveData<*> = MediatorLiveData<Any?>()
        liveDataMerger.addSource(
            liveData1
        ) { value: Any? -> liveDataMerger.setValue(value) }
    }*/

}