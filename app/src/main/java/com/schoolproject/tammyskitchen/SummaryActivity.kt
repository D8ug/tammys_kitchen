package com.schoolproject.tammyskitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        databaseReference = FirebaseDatabase.getInstance().reference.child(resources.getString(R.string.expenses_path))

        var getData = object : ValueEventListener {


            override fun onCancelled(error: DatabaseError) {
                TODO("NOT IMPLEMENTED YET (Don't think there's a reason to implement anyways tho)")
            }


            override fun onDataChange(snapshot: DataSnapshot) {

                val list = ArrayList<IncomeItem>()
                for (i in snapshot.children){
                    var name = i.child("name").value.toString()
                    var description = i.child("description").value.toString()
                    var income = i.child("price").value.toString().toDouble()
                    var UID = i.child("UID").value.toString()
                    var details = i.child("details").value.toString()
                    list += IncomeItem(income, name, description, UID, details)
                }
                val adapter = IncomeListAdapter(list)
                expensesRecyclerView.adapter = adapter
                expensesRecyclerView.layoutManager = LinearLayoutManager(this@SummaryActivity)
                expensesRecyclerView.setHasFixedSize(true)
            }
        }
        databaseReference.addValueEventListener(getData)
        databaseReference.addListenerForSingleValueEvent(getData)
    }
}
