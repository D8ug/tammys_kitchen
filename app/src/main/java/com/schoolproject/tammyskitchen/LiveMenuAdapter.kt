package com.schoolproject.tammyskitchen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.menu_item.view.*
import kotlinx.android.synthetic.main.menu_item_dialog.view.*

private lateinit var mDatabaseRef: DatabaseReference
private lateinit var mStorageReference: StorageReference

class LiveMenuAdapter(private val menuItemsList: List<LiveMenuItem>) : RecyclerView.Adapter<LiveMenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveMenuAdapter.ViewHolder {
        // creates an itemView that is on the format of LiveMenuItem, then calls ViewHolder to link the
        // items on the itemView with values and returns the created view holder in the end of this function

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        mDatabaseRef = FirebaseDatabase.getInstance().reference
        mStorageReference = FirebaseStorage.getInstance().reference

        // then it creates the listener for this itemView (so you can select a specific item in the menu)

        itemView.setOnClickListener{
            // creates the dialog:
            //Toast.makeText(it.context, "clicked", Toast.LENGTH_SHORT).show()
            val view = View.inflate(it.context, R.layout.menu_item_dialog, null)
            view.nameTextView.setText(itemView.item_name_text_view.text)
            view.descriptionTextView.text = itemView.description_text_view.text
            view.priceTextView.text = itemView.price_text_view.text
            view.imageView.setImageDrawable(itemView.image_view.drawable)
            view.imageView.maxWidth = 300
            view.imageView.maxHeight = 169

            if (FirebaseAuth.getInstance().uid == "6jd8zp2kJaPi2AU7MHWDE8iATju1") {
                view.deleteItemButton.visibility = Button.VISIBLE
                view.deleteItemButton.setOnClickListener {
                    deleteCurrentItem(it)
                }
            }

            // creates the builder for the dialog
            val builder = AlertDialog.Builder(it.context)
            builder.setView(view)

            // shows the dialog after it was created
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

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
        holder.itemID.text = currentItem.itemID
    }

    override fun getItemCount(): Int = menuItemsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.image_view
        val itemName: TextView = itemView.item_name_text_view
        val itemDescription: TextView = itemView.description_text_view
        val itemPrice: TextView = itemView.price_text_view
        val itemID: TextView = itemView.itemID
    }

    fun deleteCurrentItem(view: View) {
        Log.e("debug","entered the deleteCurrentItem() function")

        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("menu-items").child(this.menuItemsList[0].itemID)
        mDatabaseRef.removeValue()

    }

}