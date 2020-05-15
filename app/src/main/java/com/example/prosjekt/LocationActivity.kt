package com.example.prosjekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.prosjekt.Show_info



import com.example.prosjekt.Locationforecast.*
import kotlinx.android.synthetic.main.activity__location.*


class LocationActivity : AppCompatActivity() {

   private val  apiService = Apiproxyservice.create()
   private var data: Locationforecast? = null
    lateinit var hoved:TextView
    lateinit var cloud:TextView
    lateinit var fog:TextView
    lateinit var humid:TextView
    lateinit var windDir:TextView
    lateinit var windSpeed:TextView
    lateinit var wind:TextView
    lateinit var arrow: ImageButton
    lateinit var mapbutton:ImageButton
    lateinit var info: Show_info
    private var liste_med_info: ArrayList<Show_info> = ArrayList<Show_info>(50)
    private var longitude = 0.toDouble()
    private var latitude = 0.toDouble()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__location)

        CoroutineScope(Dispatchers.IO).launch {
           fetchJson()}
   }



 private suspend fun fetchJson() {
            println("INNElocation")
     latitude = intent.getDoubleExtra("lati", 2000.00)

     longitude  = intent.getDoubleExtra("longi", 2000.00)


        var call = apiService.getLocationforecast(latitude, longitude)
        call.enqueue(object : retrofit2.Callback<Locationforecast> {
            override fun onFailure(call: retrofit2.Call<Locationforecast>, t: Throwable) {
                println("FAILET IGJEN")
            }

            override fun onResponse(
                call: retrofit2.Call<Locationforecast>,
                response: retrofit2.Response<Locationforecast>
            ) {
                if (response.isSuccessful) { //
                    data = response.body()

                    //sette info for første side været (kl nå)
                    create_activity()

                    //lage scrollviet for de neste 48 timene
                    create_scrollview()



                }
            }
        })



 }

    fun create_activity(){//Første gang programmet kjører vises denne været for nå

        //INFO
        hoved = findViewById(R.id.hovedtext)
        cloud= findViewById(R.id.cloud)
        fog = findViewById(R.id.fog)
        humid = findViewById(R.id.humid)
        windDir = findViewById(R.id.winddirection)
        windSpeed = findViewById(R.id.windspeed)
        arrow = findViewById(R.id.arrow)
        mapbutton = findViewById(R.id.mapbutton)

        val time = data?.product?.time?.get(0)?.from
        val tid = time!!.split("T" ,":")
        println(tid)
        hoved.text=  data?.product?.time?.get(0)?.location?.temperature?.value+ "°C"
        cloud.text = " Skyer: " + data?.product?.time?.get(0)?.location?.cloudiness?.percent
        fog.text = "Tåke: \n" + data?.product?.time?.get(0)?.location?.fog?.percent
        humid.text = " Luftfuktighet: \n" + data?.product?.time?.get(0)?.location?.humidity?.value
        windDir.text = "Vindretning: "+ data?.product?.time?.get(0)?.location?.windDirection?.name + "\n Grader:"+ data?.product?.time?.get(0)?.location?.windDirection?.deg
        windSpeed.text = "Vindstyrke: \n" + data?.product?.time?.get(0)?.location?.windSpeed?.mps

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

        //sette bakgrunnen
        set_DayNight(tid[1])
    }

    fun create_scrollview(){
        //hente alle 48 imagebuttonene
      var liste =  listOf<ImageButton>(findViewById(R.id.id1), findViewById(R.id.id2), findViewById(R.id.id3), findViewById(R.id.id4), findViewById(R.id.id5),
          findViewById(R.id.id6), findViewById(R.id.id7), findViewById(R.id.id8),findViewById(R.id.id9),findViewById(R.id.id10), findViewById(R.id.id11),
          findViewById(R.id.id12), findViewById(R.id.id13), findViewById(R.id.id14), findViewById(R.id.id15), findViewById(R.id.id16), findViewById(R.id.id17),
          findViewById(R.id.id18), findViewById(R.id.id19), findViewById(R.id.id20), findViewById(R.id.id21), findViewById(R.id.id22) , findViewById(R.id.id23),
          findViewById(R.id.id24),findViewById(R.id.id25), findViewById(R.id.id26),findViewById(R.id.id27), findViewById(R.id.id28), findViewById(R.id.id29),
          findViewById(R.id.id30),findViewById(R.id.id31),findViewById(R.id.id32), findViewById(R.id.id33), findViewById(R.id.id34) , findViewById(R.id.id35),
          findViewById(R.id.id36), findViewById(R.id.id37),findViewById(R.id.id38), findViewById(R.id.id39), findViewById(R.id.id40), findViewById(R.id.id41),
          findViewById(R.id.id42), findViewById(R.id.id43), findViewById(R.id.id44), findViewById(R.id.id45),findViewById(R.id.id46) , findViewById(R.id.id47),
          findViewById(R.id.id48))
        //hente alle 48 textviewene
        var list =  listOf<TextView>(findViewById(R.id.iD1), findViewById(R.id.iD2), findViewById(R.id.iD3), findViewById(R.id.iD4), findViewById(R.id.iD5),
            findViewById(R.id.iD6), findViewById(R.id.iD7), findViewById(R.id.iD8),findViewById(R.id.iD9),findViewById(R.id.iD10), findViewById(R.id.iD11),
            findViewById(R.id.iD12), findViewById(R.id.iD13), findViewById(R.id.iD14), findViewById(R.id.iD15), findViewById(R.id.iD16), findViewById(R.id.iD17),
            findViewById(R.id.iD18), findViewById(R.id.iD19), findViewById(R.id.iD20), findViewById(R.id.iD21), findViewById(R.id.iD22) , findViewById(R.id.iD23),
            findViewById(R.id.iD24),findViewById(R.id.iD25), findViewById(R.id.iD26),findViewById(R.id.iD27), findViewById(R.id.iD28), findViewById(R.id.iD29),
            findViewById(R.id.iD30),findViewById(R.id.iD31),findViewById(R.id.iD32), findViewById(R.id.iD33), findViewById(R.id.iD34) , findViewById(R.id.iD35),
            findViewById(R.id.iD36), findViewById(R.id.iD37),findViewById(R.id.iD38), findViewById(R.id.iD39), findViewById(R.id.iD40), findViewById(R.id.iD41),
            findViewById(R.id.iD42), findViewById(R.id.iD43), findViewById(R.id.iD44), findViewById(R.id.iD45),findViewById(R.id.iD46) , findViewById(R.id.iD47),
            findViewById(R.id.iD48))

        //legge iconer for knappene + klokkeslett
            set_dataImage_forcast(liste,list)



        //gjøre dem clickbare for å få info

        for(item in liste){
            item.setOnClickListener {
                var plass = liste.indexOf(item)
                 show_info(liste_med_info.get(plass))

            }
        }
    }

    fun get_info(plass:Int): Show_info {
        info = Show_info(
        hoved =  data?.product?.time?.get(plass)?.location?.temperature?.value+ "°C",
        cloud = " Skyer: \n" + data?.product?.time?.get(plass)?.location?.cloudiness?.percent+ "%",
        fog = "Tåke: \n" + data?.product?.time?.get(plass)?.location?.fog?.percent+ "%",
        humid= " Luftfuktighet: \n" + data?.product?.time?.get(plass)?.location?.humidity?.value,
        windDir = "Vindretning: "+ data?.product?.time?.get(plass)?.location?.windDirection?.name + "\n Grader:"+ data?.product?.time?.get(plass)?.location?.windDirection?.deg,
        windSpeed = "Vindstyrke: \n" + data?.product?.time?.get(plass)?.location?.windSpeed?.mps+ "Meter per sekund")
        return info

    }

    fun show_info( button: Show_info ){
        hoved.text= button.hoved
        cloud.text =button.cloud
        fog.text =button.fog
        humid.text = button.humid
        windDir.text = button.windDir
        windSpeed.text = button.windSpeed
    }

    fun set_dataImage_forcast(liste: List<ImageButton>, list: List<TextView>) {
        var teller = 0;
        for (nr in 0..47) {
            if (nr in 0..3) { //de 3 første har 2 mellom seg
                teller += 2
                val clouds = data?.product?.time?.get(teller)?.location?.cloudiness?.percent?.toDouble()
                val time = data?.product?.time?.get(teller)?.from
                val tid = time!!.split("T" ,":")
                val kl = tid[1]

                //sette iconene
                set_image(clouds!!, liste.get(nr), kl)
                //hente og legge til info i liste_med_info
                liste_med_info.add( get_info(teller))



                //Legg til text klokkeslett
                for(textview in list){
                    list.get(nr).text = kl
                }

            } else { //resten har 3 mellom seg
                teller += 3
                val clouds = data?.product?.time?.get(teller)?.location?.cloudiness?.percent?.toDouble()
                val time = data?.product?.time?.get(teller)?.from
                val tid = time!!.split("T" ,":")
                val kl = tid[1]

                set_image(clouds!!, liste.get(nr), kl)

                //hente og legge til info i liste_med_info
                liste_med_info.add(get_info(teller))

                //Legg til text klokkeslett
                for(textview in list) {
                    list.get(nr).text = kl
                }
            }
        }

    }

    fun set_image(clouds: Double, icon: ImageButton, kl: String){

        //sett natt icon hvis det er mørkt
        if(kl.toInt() in 6..22  ){
            //sjekk for skyprosentnivå
            //hvis den er mellom 0% og 12,5%
            if(clouds in 0.0..12.5){
                icon.setImageResource(R.drawable.sol)
            } else if(clouds in 12.5..37.5){
                icon.setImageResource(R.drawable.lettskyet)
            } else if(clouds in 37.5..62.5){
                icon.setImageResource(R.drawable.skyet)
            } else{ //63.5 og utover
                icon.setImageResource(R.drawable.overskyet)
            }
        }else{
            icon.setImageResource(R.drawable.maane)
        }



    }

    fun set_DayNight(time: String){
        var bakgrunn: ImageView = findViewById(R.id.Bakgrunn)
        if(time.toInt() in 6..22 ){ //sett på morgen bakgrunn
            bakgrunn.setImageResource(R.drawable.dagbakgrunn)
            bakgrunn.setAdjustViewBounds(true);
            bakgrunn.setScaleType(ImageView.ScaleType.FIT_XY)
        }
        else{ //sett på nattbackground
            bakgrunn.setImageResource(R.drawable.cloudy_night)
            bakgrunn.setAdjustViewBounds(true);
            bakgrunn.setScaleType(ImageView.ScaleType.FIT_XY)
        }
    }




    }



/*
  var id1: ImageButton= findViewById(R.id.id1); var id2: ImageButton= findViewById(R.id.id2); var id3: ImageButton= findViewById(R.id.id3)
        var id4: ImageButton= findViewById(R.id.id4);var id5: ImageButton= findViewById(R.id.id5);var id6: ImageButton= findViewById(R.id.id6)
        var id7: ImageButton= findViewById(R.id.id7);var id8: ImageButton= findViewById(R.id.id8);var id9: ImageButton= findViewById(R.id.id9)
        var id10: ImageButton= findViewById(R.id.id10) ;var id11: ImageButton= findViewById(R.id.id11);var id12: ImageButton= findViewById(R.id.id12)
        var id13: ImageButton= findViewById(R.id.id13); var id14: ImageButton= findViewById(R.id.id14); var id15: ImageButton= findViewById(R.id.id15)
        var id16: ImageButton= findViewById(R.id.id16);var id17: ImageButton= findViewById(R.id.id17);var id18: ImageButton= findViewById(R.id.id18)
        var id19: ImageButton= findViewById(R.id.id19);var id20: ImageButton= findViewById(R.id.id20);var i21: ImageButton= findViewById(R.id.id21)
        var id22: ImageButton= findViewById(R.id.id22) ;var id23: ImageButton= findViewById(R.id.id23);var id24: ImageButton= findViewById(R.id.id24)
        var id25: ImageButton= findViewById(R.id.id25); var id26: ImageButton= findViewById(R.id.id26); var id27: ImageButton= findViewById(R.id.id27)
        var id28: ImageButton= findViewById(R.id.id28);var id29: ImageButton= findViewById(R.id.id29);var id30: ImageButton= findViewById(R.id.id30)
        var id31: ImageButton= findViewById(R.id.id31);var id32: ImageButton= findViewById(R.id.id32);var id33: ImageButton= findViewById(R.id.id33)
        var id34: ImageButton= findViewById(R.id.id34) ;var id35: ImageButton= findViewById(R.id.id35);var id36: ImageButton= findViewById(R.id.id36)
        var id37: ImageButton= findViewById(R.id.id37); var id38: ImageButton= findViewById(R.id.id38); var id39: ImageButton= findViewById(R.id.id39)
        var id40: ImageButton= findViewById(R.id.id40);var id41: ImageButton= findViewById(R.id.id41);var id42: ImageButton= findViewById(R.id.id42)
        var id43: ImageButton= findViewById(R.id.id43);var id44: ImageButton= findViewById(R.id.id44);var id45: ImageButton= findViewById(R.id.id45)
        var id46: ImageButton= findViewById(R.id.id46) ;var id47: ImageButton= findViewById(R.id.id47);var id48: ImageButton= findViewById(R.id.id48))

   println(data?.product?.time?.get(0))
        println(data?.product?.time?.get(1))
        println(data?.product?.time?.get(2))
        println(data?.product?.time?.get(3))
            println(data?.product?.time?.get(4))
        println(data?.product?.time?.get(5))
        println(data?.product?.time?.get(6))
        println(data?.product?.time?.get(7))
        println(data?.product?.time?.get(8))
        println(data?.product?.time?.get(9))
        println(data?.product?.time?.get(10))
        println(data?.product?.time?.get(11))
        println(data?.product?.time?.get(12))
        println(data?.product?.time?.get(13))
        println(data?.product?.time?.get(14))
        println(data?.product?.time?.get(15))
        println(data?.product?.time?.get(16))
        println(data?.product?.time?.get(17))
        println(data?.product?.time?.get(18))
        println(data?.product?.time?.get(19))
        println(data?.product?.time?.get(20))
        println(data?.product?.time?.get(21))
        println(data?.product?.time?.get(22))
        println(data?.product?.time?.get(23))
        println(data?.product?.time?.get(24))
        println(data?.product?.time?.get(25))
        println(data?.product?.time?.get(26))
        println(data?.product?.time?.get(27))
        println(data?.product?.time?.get(28))
        println(data?.product?.time?.get(29))
        println(data?.product?.time?.get(30))
        println(data?.product?.time?.get(31))
        println(data?.product?.time?.get(32))
        println(data?.product?.time?.get(33))
        println(data?.product?.time?.get(34))
        println(data?.product?.time?.get(35))
        println(data?.product?.time?.get(36))
        println(data?.product?.time?.get(37))
        println(data?.product?.time?.get(38))
        println(data?.product?.time?.get(39))
        println(data?.product?.time?.get(40))
        println(data?.product?.time?.get(41))
        println(data?.product?.time?.get(42))
        println(data?.product?.time?.get(43))
        println(data?.product?.time?.get(44))
        println(data?.product?.time?.get(45))
        println(data?.product?.time?.get(46))
        println(data?.product?.time?.get(47))
        println(data?.product?.time?.get(48))
 */