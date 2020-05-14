package com.example.prosjekt.RSS

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.prosjekt.R


class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
    var txtPubdate = itemView.findViewById(R.id.txtPubdate) as TextView
    var txtContent = itemView.findViewById(R.id.txtContent) as TextView
    // var txtInfo = itemView.findViewById(R.id.txtInfo) as TextView
}

class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context): RecyclerView.Adapter<FeedViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(mContext)

    //setter inn verdier fra varsel på gitt posisjon
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubdate.text = rssObject.items[position].pubDate
        holder.txtContent.text = rssObject.items[position].content

    }

    override fun getItemCount() : Int {
        return rssObject.items.size
    }

    //syntaks
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : FeedViewHolder {
        val cardView = inflater.inflate(R.layout.alert, parent, false) as CardView

        return FeedViewHolder(cardView)
    }
}