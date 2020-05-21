package com.example.prosjekt.Repository


import androidx.lifecycle.MutableLiveData
import com.example.prosjekt.RSS.RSSObject
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RSSRepository {

     val liveDataRSS = MutableLiveData<RSSObject>()

    //bygger linken ut fra koordinater sendt inn fra aktiviteten
    private fun buildLink(lat : Double, long: Double) : String {
        val rssLink = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fin2000-apiproxy.ifi.uio.no%2Fweatherapi%2Fmetalerts%2F1.1%2F%3Flat%3D"

        val latString = "%.6f".format(lat)
        val longString = "%.6f".format(long)
        val url = StringBuilder(rssLink)

        url.append(latString)
        url.append("%26lon%3D")
        url.append(longString)

        return url.toString()
    }


    //bruker fuel til å hente ut string fra objektet, og konverterer fra json til RSSObjekt ved hjelp av gson
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