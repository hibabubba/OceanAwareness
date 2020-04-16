package com.example.prosjektin2000


import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prosjekt.Adapter.FeedAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val RSS_link = "https://in2000-proxy.ifi.uio.no/yr"

    val longitude: TextView = findViewById(R.id.longitude)
    val latitude: TextView = findViewById(R.id.latitude)
    private val RSS_to_JSON = "locationforecast/1.9/.json?lat=$latitude&lon=$longitude"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val Kart = findViewById<Button>(R.id.button)
        val Vær = findViewById<Button>(R.id.button2)
        val Havet = findViewById<Button>(R.id.button3)

        //toolbar.title = "NEWS"
       // setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false)

        recyclerview.layoutManager = linearLayoutManager

        loadRss()
    }

    private fun loadRss() {

        val loadRssAsync = object:AsyncTask<String,String,String>(){

            internal  var mDialog = ProgressDialog(this@MainActivity)

            override fun onPreExecute() {
                mDialog.setMessage("Please Wait...")
                mDialog.show()
            }

            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                var rssObject:RSSObject
                rssObject = Gson().fromJson<RSSObject>(result, RSSObject::class.java)
                val adapter = FeedAdapter(rssObject,baseContext)
                recyclerview.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun doInBackground(vararg p0: String): String {
                val result:String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(p0[0]).toString()
                return result
            }
        }

        val url_get_data = StringBuilder(RSS_to_JSON)
        url_get_data.append(RSS_link)
        loadRssAsync.execute(url_get_data.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_refresh)
            loadRss()
        return true
    }
}