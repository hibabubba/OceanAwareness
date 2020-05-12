package com.example.prosjekt

import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FulltextAdapter (fulltext : TextView, button : ImageButton, full : Boolean, type : String) {
    val currentFull = full
    val fullText = fulltext
    val button = button
    val type = type


    fun changeState(){
        if (type == "app") {
            if (!currentFull) {
                putTextApp()
            } else {
                hideText()
            }
        } else {
            if (!currentFull) {
                putTextRedning()
            } else {
                hideText()
            }
        }
    }

    private fun putTextApp(){
        fullText.text = "Denne appen er utviklet av blablabla"
        button.setImageResource(R.drawable.ic_drop_up)
    }

    private fun hideText(){
        fullText.text = ""
        button.setImageResource(R.drawable.ic_drop_down)

    }

    private fun putTextRedning() {
        fullText.text = "Denne appen er utviklet av blablabla"
        button.setImageResource(R.drawable.ic_drop_up)
    }

}