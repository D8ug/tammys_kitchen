package com.schoolproject.tammyskitchen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu_item.view.*

class MenuAdapter(private val menuItemsList: List<MenuItem>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        // creates an itemView that is on the format of MenuItem, then calls ViewHolder to link the
        // items on the itemView with values and returns the created view holder
        return ViewHolder(itemView)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        /*
        This function handles all the changes of the MenuItem based on the given parameters after the
        onCreateViewHolder creates for us a ViewHolder and links all the parameters needed
         */
        val currentItem = menuItemsList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
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