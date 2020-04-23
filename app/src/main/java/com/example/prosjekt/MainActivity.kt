package com.example.prosjektin2000


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjekt.Adapter.FeedAdapter
import com.example.prosjekt.R
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    lateinit var RSS_to_JSON: String
    lateinit var RSS_link: String
    val gson = Gson()
    var mutableList: MutableList<RSSObject> = mutableListOf()
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(IO).launch {
            API()
        }

        RSS_link = "https://in2000-proxy.ifi.uio.no/yr"

        val longitude: TextView = findViewById(R.id.longitude)
        val latitude: TextView = findViewById(R.id.latitude)
        val lat = latitude.text.toString()
        val long = longitude.text.toString()
        RSS_to_JSON = "locationforecast/1.9/.json?lat=$latitude&lon=$longitude"

        val Kart = findViewById<Button>(R.id.button)
        val Vær = findViewById<Button>(R.id.button2)
        val Havet = findViewById<Button>(R.id.button3)

        //toolbar.title = "NEWS"
        // setSupportActionBar(toolbar)
    }


    private suspend fun API() {
        try {
            delay(1000)
            val response = Fuel.get(RSS_link).awaitString()

            val userArray: Array<RSSObject> = gson.fromJson(response, Array<RSSObject>::class.java)
            if (userArray.isEmpty()) {
                withContext(Main){
                    Toast.makeText(this@MainActivity, "Could not load API", Toast.LENGTH_SHORT).show()
                }
            } else {
                for (obj: RSSObject in userArray) {
                    mutableList.add(obj)
                }
            }
        } catch (e: Exception) {
            println(e)
        }
        make()
    }

    private fun makeSpinner(){
        viewManager = LinearLayoutManager(this@MainActivity)
        viewAdapter = FeedAdapter(RSSObject, Context)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }
    private suspend fun make(){
        withContext(Main) {
            makeSpinner()
            indeterminateBar.visibility = View.INVISIBLE
        }
    }
}



