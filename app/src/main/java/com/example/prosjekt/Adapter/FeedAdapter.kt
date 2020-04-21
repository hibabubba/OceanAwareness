package com.example.prosjekt.Adapter

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
import com.example.prosjektin2000.ItemClickListener
import com.example.prosjektin2000.RSSObject


class FeedViewHolder (itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    var txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
    var txtPubdate = itemView.findViewById(R.id.txtPubdate) as TextView
    var txtContent = itemView.findViewById(R.id.txtContent) as TextView
    // var lesMerButton = itemView.findViewById(R.id.LesMerButton) as Button
    private var itemClickListener: ItemClickListener?=null

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }
}

class FeedAdapter (private val rssObject: RSSObject, private val mContext: Context): RecyclerView.Adapter<FeedViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(mContext)

    //setter inn verdier fra varsel på gitt posisjon
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubdate.text = rssObject.items[position].pubDate
        holder.txtContent.text = rssObject.items[position].content
        /*holder.lesMerButton.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
            mContext.startActivity(browserIntent)
        }*/

        /*//Dersom en varsel klikkes på åpnes nettleser
        holder.setItemClickListener(ItemClickListener { view, position, isLongClick ->
            if (!isLongClick) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                mContext.startActivity(browserIntent)
            }
        })*/
    }

    override fun getItemCount() : Int {
        return rssObject.items.size
    }

    //syntaks
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : FeedViewHolder {
        val cardView = inflater.inflate(R.layout.row, parent, false) as CardView

        return FeedViewHolder(cardView)
    }

}