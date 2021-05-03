package com.schoolproject.tammyskitchen

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.income_item.view.*

private lateinit var mDatabaseRef: DatabaseReference

class IncomeListAdapter (private val incomeItems: List<IncomeItem>) : RecyclerView.Adapter<IncomeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeListAdapter.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.income_item, parent, false)
        mDatabaseRef = FirebaseDatabase.getInstance().reference


        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = incomeItems[position]

        holder.incomeName.text = currentItem.name
        holder.incomeDescription.text = currentItem.description
        holder.incomeUID.text = currentItem.UID
        if (currentItem.income >= 0) {
            holder.incomeValue.text = "+${currentItem.income}₪"
            holder.incomeValue.setTextColor(Color.GREEN)
        }
        else {
            holder.incomeValue.text = "-${currentItem.income}₪"
            holder.incomeValue.setTextColor(Color.RED)
        }
    }

    // returns the item count (which also equals to the size of incomeItems)
    override fun getItemCount(): Int = incomeItems.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val incomeValue: TextView = itemView.income_text_view
        val incomeName: TextView = itemView.item_name_text_view
        val incomeDescription: TextView = itemView.income_description_text_view
        val incomeUID: TextView = itemView.UID
    }
}