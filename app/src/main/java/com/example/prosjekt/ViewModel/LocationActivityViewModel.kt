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

    var liveData: MutableLiveData<Locationforecast> =
        repository.liveDataForecast //MutableLiveData<RSSObject>()


    fun setCustomValue(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.fetchForecast(lat, long)
        }
    }

    fun getData(): LiveData<Locationforecast> {
        return liveData
    }

    fun set_dataImage( tellerregn: Int, tell: Int , nr: Int , data: Locationforecast?): SetImage {
            var teller= tell
            var tellerregn= tellerregn
            if (nr in 0..3) { //de 3 første har 2 mellom seg
                teller += 2
                tellerregn +=2
                val clouds =
                    data?.product?.time?.get(teller)?.location?.cloudiness?.percent?.toDouble()
                val precipitation = data?.product?.time?.get(tellerregn)?.location?.precipitation?.value?.toDouble()
                val time = data?.product?.time?.get(teller)?.from
                val tid = time!!.split("T", ":")
                val kl = tid[1]

                setImage = SetImage(clouds = clouds, kl = kl, teller = teller, tellerregn = tellerregn, precipitation= precipitation )


            } else { //resten har 3 mellom seg
                teller += 3
                tellerregn +=3
                val clouds =
                    data?.product?.time?.get(teller)?.location?.cloudiness?.percent?.toDouble()
                val precipitation = data?.product?.time?.get(tellerregn)?.location?.precipitation?.value?.toDouble()
                val time = data?.product?.time?.get(teller)?.from
                val tid = time!!.split("T", ":")
                val kl = tid[1]

                setImage = SetImage(clouds = clouds, kl = kl, teller = teller, tellerregn = tellerregn, precipitation= precipitation)


            }

        return setImage

    }

    fun set_image_logic( setImage: SetImage): Int {
        var i:Int =-1
        if (setImage.kl.toInt() in 6..22) {
            //sjekk for skyprosentnivå
            //hvis den er mellom 0% og 12,5%
            if(setImage.clouds != null && setImage.precipitation != null){
                if (setImage.clouds in 0.0..12.5) {
                    i=  R.drawable.sol
                } else if (setImage.clouds in 12.5..37.5) {
                    i= checkRain(setImage.precipitation,R.drawable.lettskyet)
                } else if (setImage.clouds in 37.5..62.5) {
                    i= checkRain(setImage.precipitation,R.drawable.skyet)
                } else { //63.5 og utover
                    i= checkRain(setImage.precipitation,R.drawable.overskyet)
                }
            }
        }else {
            i= R.drawable.maane  //sett natt icon hvis det er mørkt
        }
        return i
    }

    fun checkRain(precipitation: Double, sky: Int) : Int{
        if(precipitation > 2.0){
            return R.drawable.nedbor
        } else{
            return sky
        }
    }
}