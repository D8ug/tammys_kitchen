package com.schoolproject.tammyskitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_summary.*
import java.text.DecimalFormat

class SummaryActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        databaseReference = FirebaseDatabase.getInstance().reference.child(resources.getString(R.string.expenses_path))

        var getData = object : ValueEventListener {


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SummaryActivity, "An issue has occurred when connecting to the database, please check your internet and try again.", Toast.LENGTH_LONG).show()
                Log.e("Error", "DatabaseError", error.toException())
                finish()
            }


            override fun onDataChange(snapshot: DataSnapshot) {
                var totalSum = 0.0
                var posSum = 0.0
                var negSum = 0.0
                val list = ArrayList<IncomeItem>()
                for (i in snapshot.children){
                    var name = i.child("name").value.toString()
                    var description = i.child("description").value.toString()
                    var income = i.child("income").value.toString().toDouble()
                    var UID = i.child("uid").value.toString()
                    var details = i.child("details").value.toString()
                    list += IncomeItem(income, name, description, UID, details)
                    totalSum += income
                    if (income > 0) posSum += income
                    else negSum += income
                }
                val adapter = IncomeListAdapter(list)
                expensesRecyclerView.adapter = adapter
                expensesRecyclerView.layoutManager = LinearLayoutManager(this@SummaryActivity)
                expensesRecyclerView.setHasFixedSize(true)
                val currencyFormat = DecimalFormat("#,##0.##")

                var formattedSum: String = currencyFormat.format(totalSum)
                sumTotalTextView.text = formattedSum + "₪"
                formattedSum = currencyFormat.format(negSum)
                negSumTextView.text = formattedSum + "₪"
                formattedSum = currencyFormat.format(posSum)
                posSumTextView.text = formattedSum + "₪"
            }
        }
        databaseReference.addValueEventListener(getData)
        databaseReference.addListenerForSingleValueEvent(getData)
    }
}
