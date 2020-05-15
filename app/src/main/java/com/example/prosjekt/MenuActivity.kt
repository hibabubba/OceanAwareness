package com.example.prosjekt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjekt.RSS.ApiproxyserviceRSS
import com.example.prosjekt.RSS.FeedAdapter
import com.example.prosjekt.RSS.RSSObject
import com.example.prosjekt.Repository.Repository
import com.example.prosjekt.Service.Service
import com.example.prosjekt.ViewModel.MenuActivityViewModel
import com.example.prosjekt.ViewModelFactory.MenuActivityViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object Injection {
    val service: Service by lazy { Service() }
    val repository: Repository by lazy { Repository(service) }
    val viewModelFactory: MenuActivityViewModelFactory by lazy {
        MenuActivityViewModelFactory(repository)
    }
}

class MenuActivity : AppCompatActivity() {
    //Widgets
    private lateinit var havaktivitetButton : Button
    private lateinit var vaervarselButton : Button
    private lateinit var rssRecyclerview : RecyclerView


    //koordinater
    private var lat = 0.toDouble()
    private var long = 0.toDouble()


    //
    private val  apiService = ApiproxyserviceRSS.create()
    private lateinit var data : RSSObject



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)




        lat = intent.getDoubleExtra("lati", 2000.00)
        long = intent.getDoubleExtra("longi", 2000.00)

        println("HEI NÅ FUNKER DET BITCHESSS")
        println("activity, long: $long")
        println("activity, lat: $lat")

       /* val viewModel: MenuActivityViewModel =
            ViewModelProviders.of(this, Injection.viewModelFactory).get(MenuActivityViewModel::class.java)
*/
        val viewModel: MenuActivityViewModel by viewModels {
            Injection.viewModelFactory
        }

        val liveData: LiveData<RSSObject> = viewModel.getData()
        liveData.observe(this, Observer<RSSObject> { result ->
            println("inni observer")
            setView(result) // update UI
        })
/*
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setCustomObject(lat, long)
        }*/

        viewModel.setCustomValue(lat, long)





        havaktivitetButton = findViewById(R.id.havaktivitetButton)
        vaervarselButton = findViewById(R.id.vaervarselButton)
        rssRecyclerview = findViewById(R.id.rssRecyclerView)
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        rssRecyclerview.layoutManager = linearLayoutManager





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

    private fun setView(result: RSSObject) {
        println("setview")
        val adapter = FeedAdapter(result,baseContext)
        rssRecyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        if (adapter.itemCount == 0) {
            val ekstrem = findViewById<View>(R.id.ekstremTextView)
            ekstrem.visibility = View.GONE

        }

    }


/*


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
    }*/

    //Bruker feed-adapter til å sette recyclerview


/*
    //Lager et RSS-objekt ved å hente ut info fra APIet
    suspend fun lagListe(url: String) : RSSObject {
        val response = Fuel.get(url).awaitString()
        return Gson().fromJson<RSSObject>(response, RSSObject::class.java)

    }*/

}

