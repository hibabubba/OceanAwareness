package com.example.prosjekt.OceanAwareness.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjekt.R
import com.example.prosjekt.RSS.FeedAdapter
import com.example.prosjekt.RSS.RSSObject
import com.example.prosjekt.OceanAwareness.Activity.ViewModel.MenuActivityViewModel




class MenuActivity : AppCompatActivity() {
    //Widgets
    private lateinit var havaktivitetButton: Button
    private lateinit var vaervarselButton: Button
    private lateinit var rssRecyclerview: RecyclerView
    private lateinit var arrow: ImageButton
    private val viewModel: MenuActivityViewModel = MenuActivityViewModel()

    //Koordinater
    private var lat = 0.toDouble()
    private var long = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        //Sjekker om darkmode er på
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //henter ut koordinatene
        lat = intent.getDoubleExtra("lati", 2000.00)
        long = intent.getDoubleExtra("longi", 2000.00)

        val liveData: LiveData<RSSObject> = viewModel.getData()
        liveData.observe(this, Observer { result ->

            val adapter = FeedAdapter(result, baseContext)
            rssRecyclerview.adapter = adapter
            adapter.notifyDataSetChanged()

            val ekstrem = findViewById<View>(R.id.ekstremTextView)
            if (adapter.itemCount == 0) {
                ekstrem.visibility = View.GONE
            } else {
                ekstrem.visibility = View.VISIBLE
            }

        })

        viewModel.getFeedByCoordinates(lat, long)

        havaktivitetButton = findViewById(R.id.havaktivitetButton)
        vaervarselButton = findViewById(R.id.vaervarselButton)
        rssRecyclerview = findViewById(R.id.rssRecyclerView)
        arrow = findViewById(R.id.arrow)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        rssRecyclerview.layoutManager = linearLayoutManager

        arrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Sender videre til neste aktivitetene
        havaktivitetButton.setOnClickListener {
            if (lat != 2000.00 && long != 2000.00) {
                val intent = Intent(this, OceanActivity::class.java)
                intent.putExtra("lati", lat)
                intent.putExtra("longi", long)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Har ikke koordinater", Toast.LENGTH_LONG).show()
            }
        }
        vaervarselButton.setOnClickListener {
            if (lat != 2000.00 && long != 2000.00) {
                val intent = Intent(this, LocationActivity::class.java)
                intent.putExtra("lati", lat)
                intent.putExtra("longi", long)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Har ikke koordinater", Toast.LENGTH_LONG).show()
            }

        }

    }


}

