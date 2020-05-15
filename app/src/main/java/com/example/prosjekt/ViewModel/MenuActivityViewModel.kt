package com.example.prosjekt.ViewModel

import androidx.lifecycle.*
import com.example.prosjekt.RSS.RSSObject
import com.example.prosjekt.Repository.Repository


class MenuActivityViewModel (private val repository: Repository) : ViewModel() {

    var liveData : MutableLiveData<RSSObject> = repository.liveDataRSS



    fun setCustomValue(lat : Double, long : Double) {
        repository.getRSSFeed(lat, long)
    }

    fun getData() : LiveData<RSSObject>{
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