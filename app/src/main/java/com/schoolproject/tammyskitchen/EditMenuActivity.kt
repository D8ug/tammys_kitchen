package com.schoolproject.tammyskitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        // sets the references for the firebase storage and database
        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("menu-items")
        mStorageReference = FirebaseStorage.getInstance().reference

        // This function is used to get the data from the database
        var getData = object : ValueEventListener {


            override fun onCancelled(error: DatabaseError) {
                TODO("NOT IMPLEMENTED YET (Don't think there's a reason to implement anyways tho)")
            }


            override fun onDataChange(snapshot: DataSnapshot) {

                val list = ArrayList<LiveMenuItem>()
                for (i in snapshot.children){
                    var itemName = i.child("itemName").value.toString()
                    var itemDescription = i.child("itemDescription").value.toString()
                    var price = i.child("price").value.toString().toInt()
                    var imageURL = i.child("imageURL").value.toString()
                    var itemID = i.child("itemID").value.toString()
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
