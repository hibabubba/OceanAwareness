package com.example.prosjekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjekt.RSS.ApiproxyserviceRSS
import com.example.prosjekt.RSS.FeedAdapter
import com.example.prosjekt.RSS.RSSObject
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.lang.StringBuilder

class MenuActivity : AppCompatActivity() {
    //Widgets
    private lateinit var havaktivitetButton : Button
    private lateinit var vaervarselButton : Button
    private lateinit var rssRecyclerview : RecyclerView


    //koordinater
    private var lat = 0.toDouble()
    private var long = 0.toDouble()

    //
    //private val  apiService = ApiproxyserviceRSS.create()
    private lateinit var data : RSSObject



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)



        havaktivitetButton = findViewById(R.id.havaktivitetButton)
        vaervarselButton = findViewById(R.id.vaervarselButton)
        rssRecyclerview = findViewById(R.id.rssRecyclerView)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        rssRecyclerview.layoutManager = linearLayoutManager

        /*
        CoroutineScope(Dispatchers.IO).launch {
            if (fetchJson()) {
                withContext(Dispatchers.Main) {
                    setView(data)

                }
            }
        }*/


        if (getCoordinates()) {
            getFeed(buildUrl())
        } else {
            Toast.makeText(this, "Funket ikke", Toast.LENGTH_LONG).show()
        }



        havaktivitetButton.setOnClickListener {
            if(lat != 2000.00 && long != 2000.00) {
                val intent = Intent(this, OceanActivity::class.java)
                //ta med meg koordinatene
                intent.putExtra("lati", lat)
                intent.putExtra("longi", long)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Har ikke koordinater", Toast.LENGTH_LONG).show()
            }
        }

        vaervarselButton.setOnClickListener {
            if(lat != 2000.00 && long != 2000.00) {
                val intent = Intent(this, LocationActivity::class.java)
                //ta med meg koordinatene
                intent.putExtra("lati", lat)
                intent.putExtra("longi", long)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Har ikke koordinater", Toast.LENGTH_LONG).show()
            }

        }

    }


    fun getCoordinates() : Boolean {
        //val bundle:Bundle? = intent.extras
        lat = intent.getDoubleExtra("lati", 2000.00)
        long = intent.getDoubleExtra("longi", 2000.00)

        if (lat != 2000.00 && long != 2000.00) {
            Toast.makeText(this, "Hentet koordinater", Toast.LENGTH_LONG).show()
            return true
        }

        Toast.makeText(this, "Kooridnater ikke funnet", Toast.LENGTH_LONG).show()
        return false
    }

    /*
    private suspend fun fetchJson() : Boolean {
        println("INNE")
        var result = false



        lat = intent.getDoubleExtra("lati", 2000.00)
        long = intent.getDoubleExtra("longi", 2000.00)

        // val latitude = 59.429317
        //   val longitude = 10.545646

        val call = apiService.getRSSfeed(lat, long)
        call.enqueue(object : retrofit2.Callback<RSSObject> {
            override fun onFailure(call: retrofit2.Call<RSSObject>, t: Throwable) {
                println("FAILET IGJEN")
            }

            override fun onResponse(
                call: retrofit2.Call<RSSObject>,
                response: retrofit2.Response<RSSObject>
            ) {
                if (response.isSuccessful) {
                    data = response.body()
                    result = true
                }
            }
        })

        return result



    }*/

    fun buildUrl()  : String {
        val rssLink = "https://api.met.no/weatherapi/metalerts/1.1/"
        val RSS_to_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url="
        val url_get_data = StringBuilder(RSS_to_JSON_API)
        url_get_data.append(rssLink)

        //gjør dette mer elegant
        //val longLat = "?lat=10.09899&lon=30.970696"
        url_get_data.append("?lat=")
        url_get_data.append(lat.toString())
        url_get_data.append("&lon=")
        url_get_data.append(long.toString())

        return url_get_data.toString()
    }



    fun getFeed(url : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = lagListe(url)

            withContext(Dispatchers.Main) {
                setView(result)

            }
        }
    }

    //Bruker feed-adapter til å sette recyclerview
    private fun setView(result: RSSObject) {
        val adapter = FeedAdapter(result,baseContext)
        rssRecyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        if (adapter.itemCount == 0) {
            val ekstrem = findViewById<View>(R.id.ekstremTextView)
            ekstrem.visibility = View.GONE

        }

    }


    //Lager et RSS-objekt ved å hente ut info fra APIet
    suspend fun lagListe(url: String) : RSSObject {
        val response = Fuel.get(url).awaitString()
        return Gson().fromJson<RSSObject>(response, RSSObject::class.java)

    }

}

