package com.schoolproject.tammyskitchen

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.expense_item_dialog.view.*
import kotlinx.android.synthetic.main.income_item.view.*
import java.text.DecimalFormat

private lateinit var mDatabaseRef: DatabaseReference


class IncomeListAdapter (private val incomeItems: List<IncomeItem>) : RecyclerView.Adapter<IncomeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.income_item, parent, false)
        mDatabaseRef = FirebaseDatabase.getInstance().reference

        itemView.setOnClickListener{
            // creates the dialog:
            //Toast.makeText(it.context, "clicked", Toast.LENGTH_SHORT).show()
            val view = View.inflate(it.context, R.layout.expense_item_dialog, null)
            view.expenseTitleTextView.text = itemView.item_name_text_view.text
            view.expenseDescriptionTextView.text = itemView.income_description_text_view.text
            view.moreDetailsTextView.text = itemView.incomeDetailsTextView.text
            view.priceTextView.text = itemView.income_text_view.text


            view.deleteItemButton.setOnClickListener {
                deleteExpenseWithID(itemView.incomeUID.text.toString())
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

    private fun deleteExpenseWithID(uid: String) {
        Log.e("debug","entered the deleteCurrentItem() function \ntrying to delete item uid $uid")

        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("expenses-and-incomes").child(uid)
        Log.d("path", mDatabaseRef.path.toString())
        mDatabaseRef.removeValue()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = incomeItems[position]

        holder.incomeName.text = currentItem.name
        holder.incomeDescription.text = currentItem.description
        holder.incomeUID.text = currentItem.uid
        holder.incomeDetails.text = currentItem.details
        val currencyFormat = DecimalFormat("#,##0.##")

        val formattedIncome: String = currencyFormat.format(currentItem.income)
        if (currentItem.income >= 0) {
            holder.incomeValue.text = "+${formattedIncome}₪"
            holder.incomeValue.setTextColor(Color.GREEN)
        }
        else {
            holder.incomeValue.text = "${formattedIncome}₪"
            holder.incomeValue.setTextColor(Color.RED)
        }
    }

    // returns the item count (which also equals to the size of incomeItems)
    override fun getItemCount(): Int = incomeItems.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val incomeValue: TextView = itemView.income_text_view
        val incomeName: TextView = itemView.item_name_text_view
        val incomeDescription: TextView = itemView.income_description_text_view
        val incomeUID: TextView = itemView.incomeUID
        val incomeDetails: TextView = itemView.incomeDetailsTextView
    }
}