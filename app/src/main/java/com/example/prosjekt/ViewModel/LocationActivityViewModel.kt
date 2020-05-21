package com.example.prosjekt.ViewModel

import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prosjekt.Dataclasses.SetImage
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.R
import com.example.prosjekt.Repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocationActivityViewModel () : ViewModel() {
    lateinit var setImage: SetImage

    private val repository = LocationRepository()

    private var liveData: MutableLiveData<Locationforecast> =
        repository.liveDataForecast //MutableLiveData<RSSObject>()


    fun setCustomValue(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.fetchForecast(lat, long)
        }
    }

    fun getData() : LiveData<Locationforecast> {
        return liveData
    }

    fun setDataImage( tellerRegn: Int, tell: Int , nr: Int , data: Locationforecast?): SetImage {
            var teller = tell
            var tellerRegn = tellerRegn
            if (nr in 0..3) { //de 3 første har 2 mellom seg
                teller += 2
                tellerRegn += 2
                val clouds =
                    data?.product?.time?.get(teller)?.location?.cloudiness?.percent?.toDouble()
                val precipitation = data?.product?.time?.get(tellerRegn)?.location?.precipitation?.value?.toDouble()
                val time = data?.product?.time?.get(teller)?.from
                val tid = time!!.split("T", ":")
                val kl = tid[1]

                setImage = SetImage(clouds = clouds, kl = kl, teller = teller, tellerregn = tellerRegn, precipitation= precipitation )


            } else { //resten har 3 mellom seg
                teller += 3
                tellerRegn +=3
                val clouds =
                    data?.product?.time?.get(teller)?.location?.cloudiness?.percent?.toDouble()
                val precipitation = data?.product?.time?.get(tellerRegn)?.location?.precipitation?.value?.toDouble()
                val time = data?.product?.time?.get(teller)?.from
                val tid = time!!.split("T", ":")
                val kl = tid[1]

                setImage = SetImage(clouds = clouds, kl = kl, teller = teller, tellerregn = tellerRegn, precipitation= precipitation)
            }
        return setImage
    }

    fun setImageLogic( setImage: SetImage) : Int {
        var i : Int = -1
        if (setImage.kl.toInt() in 6..22) {
            //sjekk for skyprosentnivå
            //hvis den er mellom 0% og 12,5%
            if(setImage.clouds != null && setImage.precipitation != null){
                i = when (setImage.clouds) {
                    in 0.0..12.5 -> {
                        R.drawable.sol
                    }
                    in 12.5..37.5 -> {
                        checkRain(setImage.precipitation,R.drawable.lettskyet)
                    }
                    in 37.5..62.5 -> {
                        checkRain(setImage.precipitation,R.drawable.skyet)
                    }
                    else -> { //63.5 og utover
                        checkRain(setImage.precipitation,R.drawable.overskyet)
                    }
                }
            }
        }else {
            i = R.drawable.maane  //sett natt icon hvis det er mørkt
        }
        return i
    }

    private fun checkRain(precipitation: Double, sky: Int) : Int {
        return if (precipitation > 2.0){
            R.drawable.nedbor
        } else{
            sky
        }
    }
}