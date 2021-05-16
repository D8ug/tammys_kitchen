package com.schoolproject.tammyskitchen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_menu.*

class EditMenuActivity : AppCompatActivity() {

    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var mStorageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)


        addItemButton.setOnClickListener {

            val intent = Intent(this, AddNewMenuItemsActivity::class.java)
            startActivity(intent)
        }


        // sets the references for the firebase storage and database
        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("menu-items")
        mStorageReference = FirebaseStorage.getInstance().reference

        // This function is used to get the data from the database
        val getData = object : ValueEventListener {


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditMenuActivity, "An issue has occurred when connecting to the database, please check your internet and try again.", Toast.LENGTH_LONG).show()
                Log.e("Error", "DatabaseError", error.toException())
                finish()
            }


            override fun onDataChange(snapshot: DataSnapshot) {

                val list = ArrayList<LiveMenuItem>()
                for (i in snapshot.children){
                    val itemName = i.child("itemName").value.toString()
                    val itemDescription = i.child("itemDescription").value.toString()
                    val price = i.child("price").value.toString().toInt()
                    val imageURL = i.child("imageURL").value.toString()
                    val itemID = i.child("itemID").value.toString()
                    list += LiveMenuItem(imageURL, itemName, itemDescription, price, itemID)
                }
                val adapter = LiveMenuAdapter(list)
                editMenuRecyclerView.adapter = adapter
                editMenuRecyclerView.layoutManager = LinearLayoutManager(this@EditMenuActivity)
                editMenuRecyclerView.setHasFixedSize(true)

            }
        }
        mDatabaseRef.addValueEventListener(getData)
        mDatabaseRef.addListenerForSingleValueEvent(getData)
    }
}
