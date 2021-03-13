package com.schoolproject.tammyskitchen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.menu_item.view.*

class LiveMenuAdapter(private val menuItemsList: List<LiveMenuItem>) : RecyclerView.Adapter<LiveMenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveMenuAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        // creates an itemView that is on the format of LiveMenuItem, then calls ViewHolder to link the
        // items on the itemView with values and returns the created view holder
        itemView.setOnClickListener{
            Log.println(Log.ERROR, "Button", "worked")
            //CustomDialogClass(this).show()
        }
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        This function handles all the changes of the MenuItem based on the given parameters after the
        onCreateViewHolder creates for us a ViewHolder and links all the parameters needed
         */
        val currentItem = menuItemsList[position]

        Glide.with(holder.imageView).load(currentItem.imageURL).into(holder.itemView.image_view)
        holder.itemPrice.text = "${currentItem.price}₪"
        holder.itemName.text = currentItem.itemName
        holder.itemDescription.text = currentItem.itemDescription
    }

    override fun getItemCount(): Int = menuItemsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.image_view
        val itemName: TextView = itemView.item_name_text_view
        val itemDescription: TextView = itemView.description_text_view
        val itemPrice: TextView = itemView.price_text_view
    }

}