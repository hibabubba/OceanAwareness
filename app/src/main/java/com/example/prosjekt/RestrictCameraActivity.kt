package com.mapbox.mapboxandroiddemo.examples.camera

import android.R
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.prosjekt.MainActivity

import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
//import com.mapbox.mapboxandroiddemo.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

import java.util.ArrayList

import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity

/**
 * Restrict the map camera to certain bounds.
 */
class RestrictCameraActivity : AppCompatActivity(), OnMapReadyCallback {

    private val points = ArrayList<List<Point>>()
    private val outerPoints = ArrayList<Point>()
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.

        // This contains the MapView in XML and needs to be called after the access token is configured.


       // mapView = findViewById(R.id.mapView)
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)
    }


    override fun onMapReady(mapboxMap: MapboxMap) {

        mapboxMap.setStyle(Style.SATELLITE_STREETS) { style ->
            // Set the boundary area for the map camera
            mapboxMap.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA)

            // Set the minimum zoom level of the map camera
            mapboxMap.setMinZoomPreference(2.0)

            showBoundsArea(style)

            showCrosshair()
        }
    }


    /**
     * Add a FillLayer to show the boundary area
     *
     * @param loadedMapStyle a Style object which has been loaded by the map
     */
    private fun showBoundsArea(loadedMapStyle: Style) {
        outerPoints.add(
            Point.fromLngLat(
                RESTRICTED_BOUNDS_AREA.northWest.longitude,
                RESTRICTED_BOUNDS_AREA.northWest.latitude
            )
        )
        outerPoints.add(
            Point.fromLngLat(
                RESTRICTED_BOUNDS_AREA.northEast.longitude,
                RESTRICTED_BOUNDS_AREA.northEast.latitude
            )
        )
        outerPoints.add(
            Point.fromLngLat(
                RESTRICTED_BOUNDS_AREA.southEast.longitude,
                RESTRICTED_BOUNDS_AREA.southEast.latitude
            )
        )
        outerPoints.add(
            Point.fromLngLat(
                RESTRICTED_BOUNDS_AREA.southWest.longitude,
                RESTRICTED_BOUNDS_AREA.southWest.latitude
            )
        )
        outerPoints.add(
            Point.fromLngLat(
                RESTRICTED_BOUNDS_AREA.northWest.longitude,
                RESTRICTED_BOUNDS_AREA.northWest.latitude
            )
        )
        points.add(outerPoints)

        loadedMapStyle.addSource(
            GeoJsonSource(
                "source-id",
                Polygon.fromLngLats(points)
            )
        )

        loadedMapStyle.addLayer(
            FillLayer("layer-id", "source-id").withProperties(
                fillColor(Color.RED),
                fillOpacity(.25f)
            )
        )
    }

    private fun showCrosshair() {
        val crosshair = View(this)
        crosshair.layoutParams = FrameLayout.LayoutParams(15, 15, Gravity.CENTER)
        crosshair.setBackgroundColor(Color.GREEN)
        mapView!!.addView(crosshair)
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    companion object {
        private val BOUND_CORNER_NW = LatLng(-8.491377105132457, 108.26584125231903)
        private val BOUND_CORNER_SE = LatLng(-42.73740968175186, 158.19629538046348)
        private val RESTRICTED_BOUNDS_AREA = LatLngBounds.Builder()
            .include(BOUND_CORNER_NW)
            .include(BOUND_CORNER_SE)
            .build()
    }
}
