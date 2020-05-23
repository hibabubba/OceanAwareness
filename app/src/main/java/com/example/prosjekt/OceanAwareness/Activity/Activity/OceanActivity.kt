package com.example.prosjekt.OceanAwareness.Activity.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatDelegate
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.R
import com.example.prosjekt.OceanAwareness.Activity.Dataclasses.ShowOceanInfo
import com.example.prosjekt.OceanAwareness.Activity.ViewModel.OceanActivityViewModel
import kotlinx.android.synthetic.main.activity_ocean.*
import java.lang.String.format

class OceanActivity : AppCompatActivity() {

    var data: Oceanforecast? = null
    private val viewModel: OceanActivityViewModel = OceanActivityViewModel()
    private var listeMedInfo: ArrayList<ShowOceanInfo> = ArrayList(50)
    private lateinit var info: ShowOceanInfo
    private lateinit var icePresence: TextView
    private lateinit var temp: TextView
    private lateinit var seaCurrentSpeed: TextView
    private lateinit var seaCurrentDirection: TextView
    private lateinit var totalWaveDirection: TextView
    private lateinit var waveHeight: TextView
    private lateinit var date: TextView
    private lateinit var location: TextView
    private lateinit var arrow: ImageButton
    private lateinit var mapbutton: ImageButton
    private var longitude = 0.toDouble()
    private var latitude = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //sjekker om darkmode er på
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        setContentView(R.layout.activity_ocean)
        latitude = intent.getDoubleExtra("lati", 2000.00)
        longitude  = intent.getDoubleExtra("longi", 2000.00)
        val lat = "%.6f".format(latitude)
        val long = "%.6f".format(longitude)

        //Sette lokasjon til activiteten
        location = findViewById(R.id.location)
        val loc = getString(R.string.koordinat)
        val locationText = format(loc,lat ,long )
        location.text = locationText

        val liveData : LiveData<Oceanforecast> = viewModel.getData()
        liveData.observe(this, Observer { result ->
            data = result
            createActivity()
            createScrollView()
            progressbar2.visibility= View.INVISIBLE
        })

        viewModel.setCustomValue(latitude, longitude)
    }

    private fun createActivity(){

     icePresence  = findViewById(R.id.icepresence)
     temp = findViewById(R.id.seatemp)
     seaCurrentSpeed= findViewById(R.id.seacurrentspeed)
     seaCurrentDirection = findViewById(R.id.seacurrentdir)
     totalWaveDirection = findViewById(R.id.totwavedir)
     waveHeight = findViewById(R.id.waveheight)
     date = findViewById(R.id.date)
     arrow = findViewById(R.id.arrow)
     mapbutton = findViewById(R.id.mapbutton)

     val tid =  data?.moxForecast?.get(1)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
     val tiden = tid!!.split("T" ,":")

     val icepresencestring = getString(R.string.icepresence)
     val icepresenceformatted = format(icepresencestring, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSeaIcePresence?.content, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSeaIcePresence?.uom)
     val totalwavedirectionstring = getString(R.string.totalwavedirection)
     val totalwavedirectionformatted = format(totalwavedirectionstring, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxMeanTotalWaveDirection?.content)
     val seacurrentdirectionstring = getString(R.string.seacurrentdirection)
     val seacurrentdirectionformatted =format(seacurrentdirectionstring, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSeaCurrentDirection?.content)
     val waveheightstring = getString(R.string.waveheight)
     val waveheightformatted = format(waveheightstring, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.content, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.uom)
     val tempstring = getString(R.string.temp)
     val tempformatted = format(tempstring, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSeaTemperature?.content)
     val seacurrentspeedstring = getString(R.string.seacurrentspeed)
     val seacurrentspeedformatted = format(seacurrentspeedstring, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSeaCurrentSpeed?.content, data?.moxForecast?.get(1)?.metnoOceanForecast?.moxSeaCurrentSpeed?.uom)
     val datestring = getString(R.string.date)
     val dateformatted = format(datestring, tiden[0])

     icePresence.text = icepresenceformatted
     totalWaveDirection.text = totalwavedirectionformatted
     seaCurrentDirection.text = seacurrentdirectionformatted
     waveHeight.text = waveheightformatted
     temp.text = tempformatted
     seaCurrentSpeed.text = seacurrentspeedformatted
     date.text = dateformatted

     //Ta deg tilbake eller til kart
     arrow.setOnClickListener {
         val intent = Intent(this, MenuActivity::class.java)
         intent.putExtra("lati", latitude)
         intent.putExtra("longi", longitude)
         startActivity(intent)
     }
     mapbutton.setOnClickListener {
         startActivity(Intent(this, MainActivity::class.java))
     }

 }


    private fun createScrollView() {
        //hente alle 48 buttonene
        val liste = listOf<Button>(findViewById(R.id.iD1), findViewById(R.id.iD2), findViewById(R.id.iD3), findViewById(R.id.iD4),
            findViewById(R.id.iD5), findViewById(R.id.iD6), findViewById(R.id.iD7), findViewById(R.id.iD8), findViewById(R.id.iD9), findViewById(R.id.iD10),
            findViewById(R.id.iD11), findViewById(R.id.iD12), findViewById(R.id.iD13), findViewById(R.id.iD14), findViewById(R.id.iD15), findViewById(R.id.iD16),
            findViewById(R.id.iD17), findViewById(R.id.iD18), findViewById(R.id.iD19), findViewById(R.id.iD20), findViewById(R.id.iD21), findViewById(R.id.iD22),
            findViewById(R.id.iD23), findViewById(R.id.iD24), findViewById(R.id.iD25), findViewById(R.id.iD26), findViewById(R.id.iD27), findViewById(R.id.iD28),
            findViewById(R.id.iD29), findViewById(R.id.iD30), findViewById(R.id.iD31), findViewById(R.id.iD32), findViewById(R.id.iD33), findViewById(R.id.iD34),
            findViewById(R.id.iD35), findViewById(R.id.iD36), findViewById(R.id.iD37), findViewById(R.id.iD38), findViewById(R.id.iD39), findViewById(R.id.iD40),
            findViewById(R.id.iD41), findViewById(R.id.iD42), findViewById(R.id.iD43), findViewById(R.id.iD44), findViewById(R.id.iD45), findViewById(R.id.iD46),
            findViewById(R.id.iD47), findViewById(R.id.iD48)
        )
        //legge klokkeslett for knappene
        setScrollView(liste)

        //gjøre dem clickbare for å få info
        for (item in liste) {
            item.setOnClickListener {
                val plass = liste.indexOf(item)
                showOceanInfo(listeMedInfo[plass])
            }
        }
    }

    private fun setScrollView(liste: List<Button>) {
        for (nr in 0..47) {
            val tid = data?.moxForecast?.get(nr+1)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
            val tiden = tid!!.split("T", ":")

            //Legg til klokkeslett i scrollbar
            val klstring = getString(R.string.kl)
            val klformatted = String.format(klstring, tiden[1] )
            liste[nr].text = klformatted
            listeMedInfo.add(getInfo(nr+1))
        }
    }

    private fun getInfo(nr: Int): ShowOceanInfo {
        val tid = data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
        val tiden = tid!!.split("T", ":")
        info =
            ShowOceanInfo(
                icepresence = "Icepresence \n       " + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaIcePresence?.content + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaIcePresence?.uom,
                totwavedir = " Bølgeretning \n       " + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxMeanTotalWaveDirection?.content + "°",
                seacurrentdir = " havstrømretning \n       " + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentDirection?.content + "°",
                waveheight = " Bølgehøyde \n       " + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.content + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.uom,
                seatemp = " Havtemperatur \n       " + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaTemperature?.content + "°C",
                seacurrentspeed = " Havstrømfart \n       " + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentSpeed?.content + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentSpeed?.uom,
                date = "Dato: " + tiden[0]
            )

        return info

    }

    private fun showOceanInfo(button : ShowOceanInfo) {
        icePresence.text = button.icepresence
        temp.text = button.seatemp
        seaCurrentSpeed.text = button.seacurrentspeed
        seaCurrentDirection.text = button.seacurrentdir
        totalWaveDirection.text = button.totwavedir
        waveHeight.text = button.waveheight
        date.text = button.date
    }

}




