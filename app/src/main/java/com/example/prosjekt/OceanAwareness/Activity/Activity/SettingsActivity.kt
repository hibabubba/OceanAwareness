package com.example.prosjekt.OceanAwareness.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.prosjekt.R


class SettingsActivity : AppCompatActivity() {

    private var currentfulltextApp: Boolean = false
    private var currentFulltextRedning: Boolean = false
    private lateinit var omAppButton: androidx.appcompat.widget.AppCompatImageButton
    private lateinit var omRedningButton: androidx.appcompat.widget.AppCompatImageButton
    private lateinit var fullTextApp: TextView
    private lateinit var fullTextRedning: TextView
    private lateinit var arrow: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        omAppButton = findViewById(R.id.omAppButton)
        omRedningButton = findViewById(R.id.omRedningButton)
        fullTextApp = findViewById(R.id.omAppTextView)
        fullTextRedning = findViewById(R.id.omRedningTextView)
        arrow = findViewById(R.id.arrow)

        findViewById<Switch>(R.id.nattSwitch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        omAppButton.setOnClickListener {
            changeState("app")
            currentfulltextApp = true
        }

        omRedningButton.setOnClickListener{
            changeState("redning")
            currentFulltextRedning = true
        }

        arrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun changeState(type : String) {
        if (type == "app") {
            if (!currentfulltextApp) {
                putTextApp()
            } else {
                hideText(type)
            }
        } else {
            if (!currentFulltextRedning) {
                putTextRedning()
            } else {
                hideText(type)
            }
        }

    }

    private fun hideText(type : String){
        if(type == "app") {
            fullTextApp.text = ""
            omAppButton.setImageResource(R.drawable.ic_drop_down)
        } else {
            fullTextRedning.text = ""
            omRedningButton.setImageResource(R.drawable.ic_drop_down)
        }
    }

    private fun putTextRedning() {
        fullTextRedning.text= getString(R.string.redningstjenesten)
        omRedningButton.setImageResource(R.drawable.ic_drop_up)
    }

    private fun putTextApp(){
        fullTextApp.text = getString(R.string.termsOfServiceInnhold)
        omAppButton.setImageResource(R.drawable.ic_drop_up)
    }

}
