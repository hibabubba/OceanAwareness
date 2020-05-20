package com.example.prosjekt.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.RSS.ApiproxyserviceRSS
import com.example.prosjekt.RSS.RSSObject
import com.example.prosjekt.Service.Service
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository() {
    //val apiRSS = ApiproxyserviceRSS.create()

     val liveDataRSS = MutableLiveData<RSSObject>()


    private fun buildLink(lat : Double, long: Double) : String {
        val rssLink = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fin2000-apiproxy.ifi.uio.no%2Fweatherapi%2Fmetalerts%2F1.1%2F%3Flat%3D"

        val latString = "%.6f".format(lat)
        val longString = "%.6f".format(long)
        val url_get_data = StringBuilder(rssLink)

        url_get_data.append(latString)
        url_get_data.append("%26lon%3D")
        url_get_data.append(longString)



        return url_get_data.toString()
    }



    suspend fun fetchRSSFeed(lat : Double, long: Double) {

        try {
            val response = Fuel.get(buildLink(lat, long)).awaitString()
            val result = Gson().fromJson<RSSObject>(response, RSSObject::class.java)

            withContext(Dispatchers.Main) {
                liveDataRSS.value = result
            }
        } catch (e : FuelError) {
            println("feil i fuel" + e.message)
        }
    }
}