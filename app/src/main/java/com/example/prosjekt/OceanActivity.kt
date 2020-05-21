package com.example.prosjekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback

class OceanActivity : AppCompatActivity() {

    private val  apiService = Apiproxyservice.create()
    private var data: Oceanforecast? = null

    private var liste_med_info: ArrayList<Show_ocean_info> = ArrayList<Show_ocean_info>(50)
    lateinit var info: Show_ocean_info

    lateinit var icepresence : TextView
    lateinit var temp: TextView
    lateinit var seacurrentspeed: TextView
    lateinit var seacurrentdirection: TextView
    lateinit var totalwavedirection: TextView
    lateinit var waveheight : TextView
    lateinit var date: TextView
    lateinit var time: TextView
    lateinit var location : TextView
    lateinit var arrow: ImageButton
    lateinit var mapbutton: ImageButton
    private var longitude = 0.toDouble()
    private var latitude = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //sjekker om darkmode er på
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        setContentView(R.layout.activity__ocean)
        CoroutineScope(Dispatchers.IO).launch {
            fetchJson()}
    }



    private suspend fun fetchJson() {

        latitude = intent.getDoubleExtra("lati", 2000.00)

        longitude = intent.getDoubleExtra("longi", 2000.00)
        //SEtte lokasjon til activiteten
        location = findViewById(R.id.location)
        location.text = "KOORDINATER: \n" + "Latitude = "+ latitude + "\nLongitude = "+ longitude



        var call = apiService.getOceanforecast(latitude, longitude)
            call.enqueue(object : retrofit2.Callback<Oceanforecast> {
                override fun onFailure(call: retrofit2.Call<Oceanforecast>, t: Throwable) {
                    println("FAILET IGJEN")
                }

                override fun onResponse(
                    call: retrofit2.Call<Oceanforecast>,
                    response: retrofit2.Response<Oceanforecast>
                ) {
                    if (response.isSuccessful) { //
                        data = response.body()
                        //lag startsiden når du åpner opp
                         create_activity()

                        //setter igjang scrollviewt
                        create_scrollView()
                    }
                }
            })

    }
 fun create_activity(){

     icepresence  = findViewById(R.id.icepresence)
     temp = findViewById(R.id.seatemp)
     seacurrentspeed= findViewById(R.id.seacurrentspeed)
     seacurrentdirection = findViewById(R.id.seacurrentdir)
     totalwavedirection = findViewById(R.id.totwavedir)
     waveheight = findViewById(R.id.waveheight)
     time = findViewById(R.id.klokkelsett)
     date = findViewById(R.id.date)
     arrow = findViewById(R.id.arrow)
     mapbutton = findViewById(R.id.mapbutton)


     icepresence.text = "Icepresence \n       " +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaIcePresence?.content + data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaIcePresence?.uom

     totalwavedirection.text = " Bølgeretning \n       " +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxMeanTotalWaveDirection?.content + "°"

     seacurrentdirection.text = " havstrømretning \n       "+
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaCurrentDirection?.content + "°"

     waveheight.text = " Bølgehøyde \n       " +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.content + data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.uom

     temp.text = " Havtemperatur \n       " +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaTemperature?.content+ "°C"

     seacurrentspeed.text = " Havstrømfart \n       " +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaCurrentSpeed?.content + data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaCurrentSpeed?.uom

     val tid =  data?.moxForecast?.get(0)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
     val tiden = tid!!.split("T" ,":")
     date.text = "Dato: " + tiden[0]



     //Sette arrow og mapbutton til clicklistener
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


    fun create_scrollView() {
        //hente alle 48 buttonene
        val liste = listOf<Button>(
            findViewById(R.id.iD1),
            findViewById(R.id.iD2),
            findViewById(R.id.iD3),
            findViewById(R.id.iD4),
            findViewById(R.id.iD5),
            findViewById(R.id.iD6),
            findViewById(R.id.iD7),
            findViewById(R.id.iD8),
            findViewById(R.id.iD9),
            findViewById(R.id.iD10),
            findViewById(R.id.iD11),
            findViewById(R.id.iD12),
            findViewById(R.id.iD13),
            findViewById(R.id.iD14),
            findViewById(R.id.iD15),
            findViewById(R.id.iD16),
            findViewById(R.id.iD17),
            findViewById(R.id.iD18),
            findViewById(R.id.iD19),
            findViewById(R.id.iD20),
            findViewById(R.id.iD21),
            findViewById(R.id.iD22),
            findViewById(R.id.iD23),
            findViewById(R.id.iD24),
            findViewById(R.id.iD25),
            findViewById(R.id.iD26),
            findViewById(R.id.iD27),
            findViewById(R.id.iD28),
            findViewById(R.id.iD29),
            findViewById(R.id.iD30),
            findViewById(R.id.iD31),
            findViewById(R.id.iD32),
            findViewById(R.id.iD33),
            findViewById(R.id.iD34),
            findViewById(R.id.iD35),
            findViewById(R.id.iD36),
            findViewById(R.id.iD37),
            findViewById(R.id.iD38),
            findViewById(R.id.iD39),
            findViewById(R.id.iD40),
            findViewById(R.id.iD41),
            findViewById(R.id.iD42),
            findViewById(R.id.iD43),
            findViewById(R.id.iD44),
            findViewById(R.id.iD45),
            findViewById(R.id.iD46),
            findViewById(R.id.iD47),
            findViewById(R.id.iD48)
        )
        //legge klokkeslett for knappene
        set_scrollView(liste)


        //gjøre dem clickbare for å få info

        for (item in liste) {
            item.setOnClickListener {
                var plass = liste.indexOf(item)
                //get_info(plass)
                show_ocean_info(liste_med_info.get(plass))
            }
        }
    }

    fun set_scrollView(liste: List<Button>) {
        for (nr in 0..47) {
            val tid =
                data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
            val tiden = tid!!.split("T", ":")
            //val kl = tiden[1]
            liste.get(nr).text = tiden[1].toString() + ":00"
            liste_med_info.add(get_info(nr))
        }}

            fun get_info(nr: Int): Show_ocean_info {
                val tid =
                    data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
                val tiden = tid!!.split("T", ":")
                info = Show_ocean_info(
                    icepresence = "Icepresence \n       " +
                            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaIcePresence?.content + data?.moxForecast?.get(
                        nr
                    )?.metnoOceanForecast?.moxSeaIcePresence?.uom,

                    totwavedir = " Bølgeretning \n       " +
                            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxMeanTotalWaveDirection?.content + "°",

                    seacurrentdir = " havstrømretning \n       " +
                            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentDirection?.content + "°",

                    waveheight = " Bølgehøyde \n       " +
                            data?.moxForecast?.get(
                                nr
                            )?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.content + data?.moxForecast?.get(
                        nr
                    )?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.uom,

                    seatemp = " Havtemperatur \n       " +
                            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaTemperature?.content + "°C",

                    seacurrentspeed = " Havstrømfart \n       " +
                            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentSpeed?.content + data?.moxForecast?.get(
                        nr
                    )?.metnoOceanForecast?.moxSeaCurrentSpeed?.uom,

                    date = "Dato: " + tiden[0]
                )

                return info

                //val tid =  data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
                //val tiden = tid!!.split("T" ,":")
                //time.text = "KL: " + tiden[1]
            }

            fun show_ocean_info(button: Show_ocean_info) {
                icepresence.text = button.icepresence
                temp.text = button.seatemp
                seacurrentspeed.text = button.seacurrentspeed
                seacurrentdirection.text = button.seacurrentdir
                totalwavedirection.text = button.totwavedir
                waveheight.text = button.waveheight
                date.text = button.date
                //time.text = button.klokkelsett
            }

        }




