package com.example.prosjekt.OceanAwareness.Activity.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import com.example.prosjekt.OceanAwareness.Activity.Dataclasses.Apiproxyservice
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.R
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource


class MainActivity : AppCompatActivity(), OnMapReadyCallback,MapboxMap.OnMapClickListener {
    private var map: MapView? = null
    private var mapboxMap: MapboxMap? = null
    private var sharedPreferences: SharedPreferences? = null
    private var longTextView: TextView? = null
    private var latTextView: TextView? = null
    private lateinit var popupWindow: PopupWindow
    private lateinit var sjekkBtn: Button
    private var savedLat: Double = 0.toDouble()
    private var savedLong: Double = 0.toDouble()


    override fun onCreate(savedInstanceState: Bundle?) {
        //Kode for nattmodus
        super.onCreate(savedInstanceState)
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        Mapbox.getInstance(this, "pk.eyJ1IjoiaGliYWJlIiwiYSI6ImNrOHVkcWQ5eDAxZWIzanFxbHpxZzhxbXcifQ.5LZQ6tZnTbUXugtbSTRG7A")

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setContentView(R.layout.activity_main)
        longTextView = findViewById(R.id.shared_pref_saved_long_textview)
        latTextView = findViewById(R.id.shared_pref_saved_lat_textview)
        sjekkBtn = findViewById(R.id.button)

        findViewById<AppCompatImageButton>(R.id.settingsButton).setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        map = this.findViewById(R.id.kart)
        map?.onCreate(savedInstanceState)
        map!!.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this@MainActivity.mapboxMap = mapboxMap

        mapboxMap.setStyle(
            //Forandre kartstilen ut i fra nattmodus knappen
            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                setTheme(R.style.darktheme)
                Style.Builder().fromUri(Style.DARK)
            } else {
                setTheme(R.style.AppTheme)
                Style.Builder().fromUri(Style.MAPBOX_STREETS)
            }

                         // Henter ut markørsymbolet på kartet
                         .withImage(
                             CLICK_LOCATION_ICON_ID, BitmapFactory.decodeResource(
                                 this@MainActivity.resources, R.drawable.marker_black
                             )
                         )

                        // Legge til en  GeoJson source
                        .withSource(GeoJsonSource(CLICK_LOCATION_SOURCE_ID))

                        // Legger til markøren med en offsett
                        .withLayer(
                            SymbolLayer(
                                CLICK_LOCATION_LAYER_ID,
                                CLICK_LOCATION_SOURCE_ID
                            )
                                .withProperties(
                                    PropertyFactory.iconImage(CLICK_LOCATION_ICON_ID),
                                    PropertyFactory.iconAllowOverlap(true),
                                   PropertyFactory.iconOffset(arrayOf(0f, -9f))
                                )
                        )
        )


        {
         sjekkBtn.visibility = View.GONE

         // Legger til minimum zoom, panning restriksjon og en clicklistener
         mapboxMap.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA)
         mapboxMap.setMinZoomPreference(3.0)
         mapboxMap.addOnMapClickListener(this@MainActivity )

         // Henter koordinatene fra shared preferences
         savedLong = getCoordinateFromSharedPref(SAVED_LONG_KEY)
         savedLat = getCoordinateFromSharedPref(SAVED_LAT_KEY)

         //  if  == 0, betyr at koordinatene ikke har blitt lagret
             if (savedLong == 0.0 && savedLat == 0.0) {
                 Toast.makeText(this@MainActivity, "Vi har ikke fått lagret begge koordinatene", Toast.LENGTH_SHORT).show()

             } else {

                 // Flytt kamera til en forrige lagrede koordinatene
                mapboxMap.animateCamera(
                     CameraUpdateFactory
                         .newCameraPosition(
                             CameraPosition.Builder()
                                 .target(LatLng(savedLat,savedLong ))
                                 .zoom(4.0)
                                 .build()
                         ), 1200
                 )
                 // Flytt markøren til en forrige lagrede koordinatene
                moveMarkerToLngLat(savedLong,savedLat)
                longTextView!!.text = String.format(" longitude: ", savedLong.toString())
                latTextView!!.text = String.format(" latitude: ", savedLat.toString())
             }

         }


    }
    override fun onMapClick(mapClickPoint: LatLng): Boolean {
        val clickLatitude = mapClickPoint.latitude
        val clickLongitude = mapClickPoint.longitude


        //vise popupvinduet etter hver click
        val layout = findViewById<ConstraintLayout>(R.id.constraint)
        ShowPopupWindow(layout, clickLatitude, clickLongitude)


        //Hente ut klikken og sette det i texten
        longTextView!!.text = clickLongitude.toString()
        latTextView!!.text = clickLatitude.toString()

        //Lagre mapclickpoint i shared preferences
        if (sharedPreferences != null) {
            putCoordinateToSharedPref(SAVED_LAT_KEY, clickLatitude)
            putCoordinateToSharedPref(SAVED_LONG_KEY, clickLongitude)
        }

        //Flytt markøren til det nylige lagrede stedet
        moveMarkerToLngLat(clickLongitude, clickLatitude )
    return true
    }

    //Flytte markøren til en ny lokasjon
    private fun moveMarkerToLngLat(newLong: Double, newLat: Double) {
            mapboxMap!!.getStyle { style ->
                val clickLocationSource =
                    style.getSourceAs<GeoJsonSource>(CLICK_LOCATION_SOURCE_ID)
                clickLocationSource?.setGeoJson(Point.fromLngLat(newLong, newLat))
            }
    }

    //Lagrer koordinater i shared Preferneces
    private fun putCoordinateToSharedPref(key: String, value: Double) {
        sharedPreferences!!.edit().putLong(key, java.lang.Double.doubleToRawLongBits(value)).apply()
    }

    //Hente ut koordinater fra shared Preferneces
    private fun getCoordinateFromSharedPref(key: String): Double {
        return java.lang.Double.longBitsToDouble(sharedPreferences!!.getLong(key, 0))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map!!.onSaveInstanceState(outState)
    }
    override fun onStart() {
        super.onStart()
        map!!.onStart()
    }
    override fun onPause() {
        super.onPause()
        map?.onPause()
    }
    override fun onStop() {
        super.onStop()
        map?.onStop()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        map?.onLowMemory()
    }
    override fun onDestroy() {
        super.onDestroy()
        map?.onDestroy()
    }

    //PopuVinduet
    private fun ShowPopupWindow (view: View,lat: Double,long: Double) {
        // inflate the layout of the popup window
        val inflater: LayoutInflater = LayoutInflater.from(this)
        getSystemService(LAYOUT_INFLATER_SERVICE)
        val popupView = inflater.inflate(R.layout.popup, null)

        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        popupWindow =  PopupWindow(popupView, width, height, focusable)

        popupView.findViewById<TextView>(R.id.latTxt).text = "Latitude: \n" + "%.6f".format(lat)
        popupView.findViewById<TextView>(R.id.longTxt).text = "Longitude: \n" + "%.6f".format(long)

        popupView.findViewById<Button>(R.id.sjekkButton).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            //ta med meg koordinatene
            intent.putExtra("lati", getCoordinateFromSharedPref(SAVED_LAT_KEY))
            intent.putExtra("longi", getCoordinateFromSharedPref(SAVED_LONG_KEY))
            startActivity(intent)
        }
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 550, 210)

    }

    companion object {
        private const val SAVED_LAT_KEY = "SAVED_LAT_KEY"
        private const val SAVED_LONG_KEY = "SAVED_LONG_KEY"
        private const val CLICK_LOCATION_SOURCE_ID = "CLICK_LOCATION_SOURCE_ID"
        private const val CLICK_LOCATION_ICON_ID = "CLICK_LOCATION_ICON_ID"
        private const val CLICK_LOCATION_LAYER_ID = "CLICK_LOCATION_LAYER_ID"

        private  val BOUND_CORNER_NW = LatLng(55.0274, 0.0328 )
        private  val BOUND_CORNER_SE = LatLng(80.66336, 30.74943)
        private val RESTRICTED_BOUNDS_AREA = LatLngBounds.Builder()
            .include(BOUND_CORNER_NW)
            .include(BOUND_CORNER_SE)
            .build()


    }

}












