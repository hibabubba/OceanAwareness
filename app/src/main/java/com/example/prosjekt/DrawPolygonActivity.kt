package com.mapbox.mapboxandroiddemo.examples.dds

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prosjekt.MainActivity
import com.example.prosjekt.R
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

import java.util.ArrayList

import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.geojson.Polygon.fromLngLats as fromLngLats1

/**
 * Draw a vector polygon on a map with the Mapbox Android SDK.
 */
class DrawPolygonActivity : AppCompatActivity() {

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
      //  Mapbox.getInstance(MainActivity, getString(R.string.mapbox_access_token))

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_polygon)

        mapView = findViewById(R.id.mapView)
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                style.run {
             //       addSource(GeoJsonSource("source-id",Polygon.fromLngLats(POINTS)))
                    addLayerBelow(
                                FillLayer("layer-id", "source-id").withProperties(
                                    fillColor(Color.parseColor("#3bb2d0"))
                                ), "settlement-label"
                            )
                }
            }
        }
    }

    companion object {

        private val POINTS = ArrayList<Any>()
        private val OUTER_POINTS = ArrayList<Any>()

        init {
            OUTER_POINTS.add(Point.fromLngLat(-122.685699, 45.522585))
            OUTER_POINTS.add(Point.fromLngLat(-122.708873, 45.534611))
            OUTER_POINTS.add(Point.fromLngLat(-122.678833, 45.530883))
            OUTER_POINTS.add(Point.fromLngLat(-122.667503, 45.547115))
            OUTER_POINTS.add(Point.fromLngLat(-122.660121, 45.530643))
            OUTER_POINTS.add(Point.fromLngLat(-122.636260, 45.533529))
            OUTER_POINTS.add(Point.fromLngLat(-122.659091, 45.521743))
            OUTER_POINTS.add(Point.fromLngLat(-122.648792, 45.510677))
            OUTER_POINTS.add(Point.fromLngLat(-122.664070, 45.515008))
            OUTER_POINTS.add(Point.fromLngLat(-122.669048, 45.502496))
            OUTER_POINTS.add(Point.fromLngLat(-122.678489, 45.515369))
            OUTER_POINTS.add(Point.fromLngLat(-122.702007, 45.506346))
            OUTER_POINTS.add(Point.fromLngLat(-122.685699, 45.522585))
            POINTS.add(OUTER_POINTS)
        }
    }
}


