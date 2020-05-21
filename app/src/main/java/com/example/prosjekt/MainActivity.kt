package com.example.prosjekt

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.WHITE
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager

//import org.osmdroid.views.MapView;
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.prosjekt.Dataclasses.Results
import com.example.prosjekt.R
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.*
//import com.mapbox.mapboxsdk.plugins.annotation.FillOptions
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.ColorUtils.colorToRgbaString
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.util.ArrayList
import okhttp3.internal.checkOffsetAndCount as checkOffsetAndCount1


class MainActivity : AppCompatActivity(), OnMapReadyCallback,MapboxMap.OnMapClickListener {
    private var map: MapView? = null
    private var mapboxMap: MapboxMap? = null
    private val points = ArrayList<List<Point>>()
    private val outerPoints = ArrayList<Point>()
    private var sharedPreferences: SharedPreferences? = null
    private var longTextView: TextView? = null
    private var latTextView: TextView? = null
    private lateinit var popupWindow : PopupWindow

    private lateinit var sjekkBtn : Button
    //private lateinit var settingsBtn : AppCompatImageButton
    private var savedLat: Double = 0.toDouble()
    private var savedLong: Double = 0.toDouble()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
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
            if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
                setTheme(R.style.darktheme)
                Style.Builder().fromUri(Style.DARK)
            } else {
                setTheme(R.style.AppTheme)
                Style.Builder().fromUri(Style.MAPBOX_STREETS)
            }

                     //Style.Builder().fromUri(Style.MAPBOX_STREETS)



                         // Add the SymbolLayer icon image to the map style
                        /* .withImage(
                             CLICK_LOCATION_ICON_ID, BitmapFactory.decodeResource(
                                 this@MainActivity.resources, R.drawable.marker_black
                             )
                         )*/
                         //.withLayer(showCrosshair())
                        // Adding a GeoJson source for the SymbolLayer icons.
                        .withSource(GeoJsonSource(CLICK_LOCATION_SOURCE_ID))

                        // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
                        // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
                        // the coordinate point. This is offset is not always needed and is dependent on the image
                        // that you use for the SymbolLayer icon.
                     /*   .withLayer(
                            SymbolLayer(CLICK_LOCATION_LAYER_ID, CLICK_LOCATION_SOURCE_ID)
                                .withProperties(
                                    PropertyFactory.iconImage(CLICK_LOCATION_ICON_ID),
                                    PropertyFactory.iconAllowOverlap(true),
                                   PropertyFactory.iconOffset(arrayOf(0f, -9f))
                                )
                        )*/


                 )


                 {
                     sjekkBtn.visibility = View.GONE


                     // Set the boundary area for the map camera(rectricted panning
                      //  showBoundsArea(style)
                     mapboxMap.setLatLngBoundsForCameraTarget(MainActivity.RESTRICTED_BOUNDS_AREA)
                     // Set the minimum zoom level of the map camera
                     mapboxMap.setMinZoomPreference(3.0)

                   //  showCrosshair()

                     mapboxMap.addOnMapClickListener(this@MainActivity )



                     // Get the coordinates from shared preferences
                     savedLong = getCoordinateFromSharedPref(SAVED_LONG_KEY)
                     savedLat = getCoordinateFromSharedPref(SAVED_LAT_KEY)


                     // Coordinates haven't been saved if both == 0
                     if (savedLong == 0.0 && savedLat == 0.0) {
                         Toast.makeText(
                             this@MainActivity,
                             "Vi har ikke fått lagt til begge koordinatene", Toast.LENGTH_SHORT
                         ).show()
                            /*
                         com.mapbox.mapboxandroiddemo.examples.labs.longTextView!!.text = String.format(
                             getString(R.string.saved_long_textview),
                             getString(R.string.not_saved_yet)
                         )
                         com.mapbox.mapboxandroiddemo.examples.labs.latTextView!!.text = String.format(
                             getString(R.string.saved_lat_textview),
                             getString(R.string.not_saved_yet)
                         )*/

                     } else {


                         // Move the camera to the previously-saved coordinates
                         mapboxMap.animateCamera(
                             CameraUpdateFactory
                                 .newCameraPosition(
                                     CameraPosition.Builder()
                                         .target(LatLng(savedLat,savedLong ))
                                         .zoom(4.0)
                                         .build()
                                 ), 1200
                         )
                            /*
                         Toast.makeText(
                             this@MainActivity,
                             getString(R.string.shared_pref_marker_placement), Toast.LENGTH_SHORT
                         ).show()*/

                         // Move the marker to the previously-saved coordinates
                        moveMarkerToLngLat(savedLong,savedLat)

                       longTextView!!.text = String.format(" longitude: ", savedLong.toString())

                        latTextView!!.text = String.format(" latitude: ", savedLat.toString())
                     }

    }


    }

    override fun onMapClick(mapClickPoint: LatLng): Boolean {
        val clickLatitude = mapClickPoint.latitude
        val clickLongitude = mapClickPoint.longitude

        val layout = findViewById<ConstraintLayout>(R.id.constraint)
        onButtonShowPopupWindowClick(layout, clickLatitude, clickLongitude)

        /*
        CoroutineScope(Dispatchers.IO).launch {
            findLocation(clickLatitude, clickLongitude)
            withContext(Main) {
                val layout = findViewById<ConstraintLayout>(R.id.constraint)
                onButtonShowPopupWindowClick(layout)
            }
        }

        val xy = Point.fromLngLat(clickLongitude, clickLatitude)
        println(xy)*/






        /*val sjekkButton = popupView.findViewById<Button>(R.id.sjekkButton)
        sjekkButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            //ta med meg koordinatene
            intent.putExtra("lati", getCoordinateFromSharedPref(SAVED_LAT_KEY))
            intent.putExtra("longi", getCoordinateFromSharedPref(SAVED_LONG_KEY))
            startActivity(intent)
        }*/

        //Hente ut cliken og sette det i texten
        longTextView!!.text = clickLongitude.toString()
        latTextView!!.text = clickLatitude.toString()


        //Save the map click point coordinates to shared preferences
        if (sharedPreferences != null) {
            putCoordinateToSharedPref(MainActivity.SAVED_LAT_KEY, clickLatitude)
            putCoordinateToSharedPref(MainActivity.SAVED_LONG_KEY, clickLongitude)


        }
/*
        //viser frem knappene når vi får koordinater, sjekk at de kommer bare når koordinatene gir mening
        sjekkBtn.visibility = View.VISIBLE


        //får hver click på en knapp gå inn i en ny activity
        sjekkBtn.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            //ta med meg koordinatene
            intent.putExtra("lati", getCoordinateFromSharedPref(SAVED_LAT_KEY))
            intent.putExtra("longi", getCoordinateFromSharedPref(SAVED_LONG_KEY))
            startActivity(intent)
        }*/


        // Move the marker to the newly-saved coordinates
        moveMarkerToLngLat(clickLongitude, clickLatitude )
        return true
    }

        /**
         * Move the SymbolLayer icon to a new location
         *
         * @param newLong the new longitude
         * @param newLat  the new latitude
        */
    private fun moveMarkerToLngLat(newLong: Double, newLat: Double) {
        // Move and display the click center layer's red marker icon to
        // wherever the map was clicked on
        mapboxMap!!.getStyle { style ->
            val clickLocationSource = style.getSourceAs<GeoJsonSource>(MainActivity.CLICK_LOCATION_SOURCE_ID)
            clickLocationSource?.setGeoJson(Point.fromLngLat(newLong, newLat))

        }


    }

        /**
         * Save a specific number to shared preferences
         *
         * @param key   the number's key
         * @param value the actual number
         */
    private fun putCoordinateToSharedPref(key: String, value: Double) {
        sharedPreferences!!.edit().putLong(key, java.lang.Double.doubleToRawLongBits(value)).apply()
    }

    /**
     * Retrieve a specific number from shared preferences
     *
     * @param key the key to use for retrieval
     * @return the saved number
     */
    internal fun getCoordinateFromSharedPref(key: String): Double {
        return java.lang.Double.longBitsToDouble(sharedPreferences!!.getLong(key, 0))
    }


    /*** Add a FillLayer to show the boundary area
     * @param loadedMapStyle a Style object which has been loaded by the map*/

    private fun showBoundsArea(loadedMapStyle: Style) {
//ABCD
        outerPoints.add(Point.fromLngLat(-20.521743, 57.159091)) //A
        outerPoints.add(Point.fromLngLat(35.502496, 57.169048)) //B
        outerPoints.add(Point.fromLngLat(35.1235455, 30.685699)) //C
        outerPoints.add(Point.fromLngLat(-30.530643, 2.660121)) //D
        outerPoints.add(Point.fromLngLat(-20.521743, 57.159091)) //A

        points.add(outerPoints)

        loadedMapStyle.addSource(
            GeoJsonSource(
                "source-id",
                Polygon.fromLngLats(points)
            )
        )

        loadedMapStyle.addLayer(
            FillLayer("layer-id", "source-id").withProperties(
                PropertyFactory.fillColor("#75CFF0")
                //,PropertyFactory.fillOpacity(.25f)
            )
        )
    }
   /* private fun showCrosshair() {
        val crosshair = View(this) //lager nytt view
        this.layoutParams = FrameLayout.LayoutParams(600, 150, Gravity.TOP) //størelsen på firkant
        this.elevation= 10F
        //crosshair.offsetLeftAndRight(10)
      /*  crosshair.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            //ta med meg koordinatene
            intent.putExtra("lati", getCoordinateFromSharedPref(SAVED_LAT_KEY))
            intent.putExtra("longi", getCoordinateFromSharedPref(SAVED_LONG_KEY))
            startActivity(intent)
        }*/
        crosshair.run {
            layoutParams = FrameLayout.LayoutParams(600, 150, Gravity.TOP) //størelsen på firkant
            elevation = 10F
            //crosshair.offsetLeftAndRight(10)
            /*  crosshair.setOnClickListener {
                  val intent = Intent(this, MenuActivity::class.java)
                  //ta med meg koordinatene
                  intent.putExtra("lati", getCoordinateFromSharedPref(SAVED_LAT_KEY))
                  intent.putExtra("longi", getCoordinateFromSharedPref(SAVED_LONG_KEY))
                  startActivity(intent)
              }*/
            setBackgroundColor(0)
        } //farge på firkant
        map!!.addView(crosshair) //legger den til i viewet
    }*/

    override fun onStart() {
        super.onStart()
        map!!.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map!!.onSaveInstanceState(outState)
    }

    public override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map?.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    public override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map?.onPause()  //needed for compass, my location overlays, v6.0.0 and up
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

    private fun onButtonShowPopupWindowClick (view : View, lat : Double, long : Double) {

        // inflate the layout of the popup window
        val inflater : LayoutInflater = LayoutInflater.from(this)
        getSystemService(LAYOUT_INFLATER_SERVICE)
        val popupView = inflater.inflate(R.layout.popup, null)

        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        popupWindow =  PopupWindow(popupView, width, height, focusable)

        popupView.findViewById<TextView>(R.id.latTxt).text = "%.6f".format(lat)
        popupView.findViewById<TextView>(R.id.longTxt).text = "%.6f".format(long)

        popupView.findViewById<Button>(R.id.sjekkButton).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            //ta med meg koordinatene
            intent.putExtra("lati", getCoordinateFromSharedPref(SAVED_LAT_KEY))
            intent.putExtra("longi", getCoordinateFromSharedPref(SAVED_LONG_KEY))
            startActivity(intent)
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

    }

    /*
    private suspend fun findLocation(lat : Double, long : Double) {
        val geoCoder = Geocoder(this)

        CoroutineScope(IO).launch {
            geoCoder.getFromLocation(lat, long, 5)
        }

    }*/

    companion object {
        private val SAVED_LAT_KEY = "SAVED_LAT_KEY"
        private val SAVED_LONG_KEY = "SAVED_LONG_KEY"
        private val CLICK_LOCATION_SOURCE_ID = "CLICK_LOCATION_SOURCE_ID"
        private val CLICK_LOCATION_ICON_ID = "CLICK_LOCATION_ICON_ID"
        private val CLICK_LOCATION_LAYER_ID = "CLICK_LOCATION_LAYER_ID"

        //
        private  val BOUND_CORNER_NW = LatLng(55.0274, 0.0328 )
        private  val BOUND_CORNER_SE = LatLng(80.66336, 30.74943)
        private val RESTRICTED_BOUNDS_AREA = LatLngBounds.Builder()
            .include(BOUND_CORNER_NW)
            .include(BOUND_CORNER_SE)
            .build()


    }





}












