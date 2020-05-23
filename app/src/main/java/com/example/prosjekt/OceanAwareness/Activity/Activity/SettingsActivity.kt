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

    private var currentFulltextApp: Boolean = false
    private var currentFulltextRedning: Boolean = false
    private lateinit var omAppButton: androidx.appcompat.widget.AppCompatImageButton
    private lateinit var omRedningButton: androidx.appcompat.widget.AppCompatImageButton
    private lateinit var fullTextApp: TextView
    private lateinit var fullTextRedning: TextView

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


        findViewById<Switch>(R.id.nattSwitch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        omAppButton.setOnClickListener {
            changeState("app")
            println("trykket på appknapp")
        }

        omRedningButton.setOnClickListener{
            changeState("redning")
            println("trykket på redningknapp")
        }

        findViewById<ImageButton>(R.id.arrow).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun changeState(type : String) {
        if (type == "app") {
            if (!currentFulltextApp) {
                println("legger til tekst om app")
                putTextApp()
            } else {
                hideText(type)
                println("fjerner tekst om redning")
            }
        } else {
            if (!currentFulltextRedning) {
                println("legger til tekst om redning")
                putTextRedning()
            } else {
                hideText(type)
                println("fjerner tekst om redning")
            }
        }

    }

    private fun hideText(type : String){
        if(type == "app") {
            fullTextApp.text = ""
            omAppButton.setImageResource(R.drawable.ic_drop_down)
            println("tekst om app fjernet")
            currentFulltextApp = false
        } else {
            fullTextRedning.text = ""
            println("tekst om redning fjernet")
            omRedningButton.setImageResource(R.drawable.ic_drop_down)
            currentFulltextRedning = false
        }
    }

    private fun putTextRedning() {
        fullTextRedning.text= getString(R.string.redningstjenesten)
        omRedningButton.setImageResource(R.drawable.ic_drop_up)
        println("tekst om redning lagt til")
        currentFulltextRedning = true
    }

    private fun putTextApp(){
        fullTextApp.text = getString(R.string.termsOfServiceInnhold)
        omAppButton.setImageResource(R.drawable.ic_drop_up)
        println("tekst om app lagt til")
        currentFulltextApp = true
    }

}
