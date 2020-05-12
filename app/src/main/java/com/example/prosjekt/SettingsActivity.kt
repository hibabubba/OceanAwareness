package com.example.prosjekt

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class SettingsActivity : AppCompatActivity() {

    private var currentfulltextApp : Boolean = false
    private var currentFulltextRedning : Boolean = false
    private lateinit var omAppButton : androidx.appcompat.widget.AppCompatImageButton
    private lateinit var omRedningButton : androidx.appcompat.widget.AppCompatImageButton
    private lateinit var fullTextApp : TextView
    private lateinit var fullTextRedning : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val nattSwitch : Switch = findViewById(R.id.nattSwitch)

        nattSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        omAppButton = findViewById(R.id.omAppButton)
        omRedningButton = findViewById(R.id.omRedningButton)
        fullTextApp = findViewById(R.id.omAppTextView)
        fullTextRedning = findViewById(R.id.omRedningTextView)

        omAppButton.setOnClickListener {
            changeState(fullTextApp, omAppButton, currentfulltextApp, "app")
            currentfulltextApp = true
        }

        omRedningButton.setOnClickListener{
            changeState(fullTextRedning, omRedningButton, currentFulltextRedning, "redning")
            currentFulltextRedning = true
        }

    }

    private fun changeState(fullText : TextView, button : ImageButton, currentState : Boolean, type : String) {
        var fullTextAdapter = FulltextAdapter(fullText, button, currentState, type)
        fullTextAdapter.changeState()

    }

}
