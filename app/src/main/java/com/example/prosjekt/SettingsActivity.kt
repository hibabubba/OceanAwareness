package com.example.prosjekt

import android.content.Intent
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
    lateinit var arrow: ImageButton
    private lateinit var mySwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val nattSwitch : Switch = findViewById(R.id.nattSwitch)

        nattSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                //setTheme(R.style.darktheme)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //setTheme(R.style.AppTheme)
            }
        }

        omAppButton = findViewById(R.id.omAppButton)
        omRedningButton = findViewById(R.id.omRedningButton)
        fullTextApp = findViewById(R.id.omAppTextView)
        fullTextRedning = findViewById(R.id.omRedningTextView)
        arrow = findViewById(R.id.arrow)

        omAppButton.setOnClickListener {
            changeState(fullTextApp, omAppButton, currentfulltextApp, "app")
            currentfulltextApp = true
        }

        omRedningButton.setOnClickListener{
            changeState(fullTextRedning, omRedningButton, currentFulltextRedning, "redning")
            currentFulltextRedning = true
        }

        arrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun changeState(fullText : TextView, button : ImageButton, currentState : Boolean, type : String) {
        val fullTextAdapter = FulltextAdapter(fullText, button, currentState, type)
        fullTextAdapter.changeState()

    }

}
