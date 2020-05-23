package com.example.prosjekt.OceanAwareness.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.OceanAwareness.Activity.Dataclasses.ShowInfo
import com.example.prosjekt.OceanAwareness.Activity.ViewModel.LocationActivityViewModel
import com.example.prosjekt.R
import kotlinx.android.synthetic.main.activity_location.*
import java.lang.String.*


class LocationActivity : AppCompatActivity() {

    var data: Locationforecast? = null
    private lateinit var hoved: TextView
    private lateinit var cloud: TextView
    private lateinit var fog: TextView
    private lateinit var humid: TextView
    private lateinit var windDir: TextView
    private lateinit var windSpeed: TextView
    private lateinit var precipitation: TextView
    private lateinit var date: TextView
    private lateinit var arrow: ImageButton
    private lateinit var mapbutton: ImageButton
    private lateinit var info: ShowInfo
    private val viewModel: LocationActivityViewModel = LocationActivityViewModel()
    private var listeMedInfo: ArrayList<ShowInfo> = ArrayList(50)
    private var longitude = 0.toDouble()
    private var latitude = 0.toDouble()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Sjekker om darkmode er på
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        setContentView(R.layout.activity_location)

        latitude = intent.getDoubleExtra("lati", 2000.00)
        longitude = intent.getDoubleExtra("longi", 2000.00)


        val liveData : LiveData<Locationforecast> = viewModel.getData()
        liveData.observe(this, Observer { result ->

            data = result
            createActivity()
            createScrollview()
            progressbar2.visibility= View.INVISIBLE

        })
        viewModel.setCustomValue(latitude, longitude)
    }

    private fun createActivity() {//Første gang programmet kjører vises været for nåværende time

        hoved = findViewById(R.id.hovedtext)
        cloud = findViewById(R.id.cloud)
        fog = findViewById(R.id.fog)
        humid = findViewById(R.id.humid)
        windDir = findViewById(R.id.winddirection)
        windSpeed = findViewById(R.id.windspeed)
        arrow = findViewById(R.id.arrow)
        mapbutton = findViewById(R.id.mapbutton)
        precipitation = findViewById(R.id.precipitation)
        date = findViewById(R.id.date)


        val time = data?.product?.time?.get(0)?.from
        val tid = time!!.split("T", ":")
        val hovedstring = getString(R.string.hoved)
        val hovedformatted = format(hovedstring, data?.product?.time?.get(0)?.location?.temperature?.value)
        val cloudstring = getString(R.string.cloud)
        val cloudformatted = format(cloudstring, data?.product?.time?.get(0)?.location?.cloudiness?.percent,"%")
        val fogstring = getString(R.string.fog)
        val fogformatted = format(fogstring, data?.product?.time?.get(0)?.location?.fog?.percent,"%" )
        val humidstring = getString(R.string.humid)
        val humidformatted = format(humidstring, data?.product?.time?.get(0)?.location?.humidity?.value,"%")
        val windDirstring = getString(R.string.windDir)
        val windDirformatted = format(windDirstring, data?.product?.time?.get(0)?.location?.windDirection?.name,data?.product?.time?.get(0)?.location?.windDirection?.deg )
        val windSpeedstring = getString(R.string.windSpeed)
        val windSpeedformatted = format(windSpeedstring, data?.product?.time?.get(0)?.location?.windSpeed?.mps)
        val datestring = getString(R.string.date)
        val dateformatted = format(datestring, tid[0])
        val precipitationstring = getString(R.string.precipitation)
        val precipitationformatted = format(precipitationstring, data?.product?.time?.get(3)?.location?.precipitation?.value, data?.product?.time?.get(3)?.location?.precipitation?.unit )


        hoved.text = hovedformatted
        cloud.text = cloudformatted
        fog.text = fogformatted
        humid.text = humidformatted
        windDir.text = windDirformatted
        windSpeed.text = windSpeedformatted
        date.text = dateformatted
        precipitation.text =precipitationformatted


        //Lager tilbakeknapper
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

    private fun createScrollview() {
        //hente alle 48 imagebuttonene
        val liste = listOf<ImageButton>(findViewById(R.id.id1), findViewById(R.id.id2), findViewById(R.id.id3), findViewById(R.id.id4),
            findViewById(R.id.id5), findViewById(R.id.id6), findViewById(R.id.id7), findViewById(R.id.id8), findViewById(R.id.id9), findViewById(R.id.id10),
            findViewById(R.id.id11), findViewById(R.id.id12), findViewById(R.id.id13), findViewById(R.id.id14), findViewById(R.id.id15), findViewById(R.id.id16),
            findViewById(R.id.id17), findViewById(R.id.id18), findViewById(R.id.id19), findViewById(R.id.id20), findViewById(R.id.id21), findViewById(R.id.id22),
            findViewById(R.id.id23), findViewById(R.id.id24), findViewById(R.id.id25), findViewById(R.id.id26), findViewById(R.id.id27), findViewById(R.id.id28),
            findViewById(R.id.id29), findViewById(R.id.id30), findViewById(R.id.id31), findViewById(R.id.id32), findViewById(R.id.id33), findViewById(R.id.id34),
            findViewById(R.id.id35), findViewById(R.id.id36), findViewById(R.id.id37), findViewById(R.id.id38), findViewById(R.id.id39), findViewById(R.id.id40),
            findViewById(R.id.id41), findViewById(R.id.id42), findViewById(R.id.id43), findViewById(R.id.id44), findViewById(R.id.id45), findViewById(R.id.id46),
            findViewById(R.id.id47), findViewById(R.id.id48)
        )
        //hente alle 48 textviewene
        val list = listOf<TextView>(findViewById(R.id.iD1), findViewById(R.id.iD2), findViewById(R.id.iD3), findViewById(R.id.iD4),
            findViewById(R.id.iD5), findViewById(R.id.iD6),findViewById(R.id.iD7), findViewById(R.id.iD8), findViewById(R.id.iD9), findViewById(R.id.iD10),
            findViewById(R.id.iD11), findViewById(R.id.iD12), findViewById(R.id.iD13), findViewById(R.id.iD14), findViewById(R.id.iD15),
            findViewById(R.id.iD16), findViewById(R.id.iD17), findViewById(R.id.iD18), findViewById(R.id.iD19), findViewById(R.id.iD20),
            findViewById(R.id.iD21), findViewById(R.id.iD22), findViewById(R.id.iD23), findViewById(R.id.iD24), findViewById(R.id.iD25),
            findViewById(R.id.iD26), findViewById(R.id.iD27), findViewById(R.id.iD28), findViewById(R.id.iD29), findViewById(R.id.iD30),
            findViewById(R.id.iD31), findViewById(R.id.iD32), findViewById(R.id.iD33), findViewById(R.id.iD34), findViewById(R.id.iD35),
            findViewById(R.id.iD36), findViewById(R.id.iD37), findViewById(R.id.iD38), findViewById(R.id.iD39), findViewById(R.id.iD40),
            findViewById(R.id.iD41), findViewById(R.id.iD42), findViewById(R.id.iD43), findViewById(R.id.iD44), findViewById(R.id.iD45),
            findViewById(R.id.iD46), findViewById(R.id.iD47), findViewById(R.id.iD48)
        )

        //legge iconer for knappene + klokkeslett
        var teller = 0
        var tellerRegn = 1
        for (nr in 0..47) {
            val liste2 : List<Int> = setDataImageForcast(tellerRegn, teller, nr, liste[nr], list[nr])
            teller = liste2[0]
            tellerRegn = liste2[1]
        }

        //Gjøre dem clickbare for å oppdatere værmeldingen
        for (item in liste) {
            item.setOnClickListener {
                val plass = liste.indexOf(item)
                showInfo(listeMedInfo[plass])

            }
        }
    }

    private fun getInfo(plass: Int, plass2: Int) : ShowInfo {

        val time = data?.product?.time?.get(plass)?.from
        val tid = time!!.split("T", ":")

        info = ShowInfo(
            hoved = data?.product?.time?.get(plass)?.location?.temperature?.value + "°C",
            cloud = " Skyer \n" + data?.product?.time?.get(plass)?.location?.cloudiness?.percent + "%",
            fog = "Tåke \n" + data?.product?.time?.get(plass)?.location?.fog?.percent + "%",
            humid = " Luftfuktighet \n     " + data?.product?.time?.get(plass)?.location?.humidity?.value + "%",
            windDir = "Vindretning: " + data?.product?.time?.get(plass)?.location?.windDirection?.name + "\n Grader: " + data?.product?.time?.get(plass)?.location?.windDirection?.deg,
            windSpeed = "Vindstyrke \n" + data?.product?.time?.get(plass)?.location?.windSpeed?.mps + "m/s",
            precipitation = "Nedbør \n" + data?.product?.time?.get(plass2)?.location?.precipitation?.value + data?.product?.time?.get(plass2)?.location?.precipitation?.unit,
            date = "Dato: " + tid[0]
        )
    return info
    }

    private fun showInfo(button: ShowInfo) {
        hoved.text = button.hoved
        cloud.text = button.cloud
        fog.text = button.fog
        humid.text = button.humid
        windDir.text = button.windDir
        windSpeed.text = button.windSpeed
        precipitation.text = button.precipitation
        date.text = button.date
    }

    private fun setDataImageForcast(tellerregn: Int,teller: Int,nr: Int,liste: ImageButton,list: TextView): List<Int> {
        //sette iconene
        val resultat = viewModel.setDataImage(tellerregn, teller, nr, data)
        val bilde: Int = viewModel.setImageLogic(resultat)

        if (bilde != -1) { //Hvis elementene er lik null
            setImage(liste, bilde)
            //hente og legge til info i listeMedInfo
            listeMedInfo.add(getInfo(resultat.teller, tellerregn))


            //Legg til klokkeslett i scrollbar
            val klstring = getString(R.string.kl)
            val klformatted = String.format(klstring, resultat.kl )
            list.text = klformatted

        } else Toast.makeText(this, getString(R.string.feilmelding), Toast.LENGTH_SHORT).show()
        return listOf(resultat.teller, resultat.tellerregn)
    }

    private fun setImage(icon: ImageButton,bilde: Int) {
        icon.setImageResource(bilde)
    }
}
