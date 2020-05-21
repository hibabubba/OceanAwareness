package com.example.prosjekt.ViewModel


import androidx.lifecycle.*
import com.example.prosjekt.RSS.RSSObject
import com.example.prosjekt.Repository.RSSRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MenuActivityViewModel (private val repository : RSSRepository) : ViewModel() {

    private var liveData : MutableLiveData<RSSObject> = repository.liveDataRSS

    //sender korrdinater til repository
    fun getFeedByCoordinates(lat : Double, long : Double) {

        GlobalScope.launch(Dispatchers.IO) {
            repository.fetchRSSFeed(lat, long)
        }
    }


    fun getData() : LiveData<RSSObject> {
        return liveData
    }
}