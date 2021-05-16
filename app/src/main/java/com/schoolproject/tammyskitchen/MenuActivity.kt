package com.schoolproject.tammyskitchen

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.random.Random
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.menu_item.view.*

class MenuActivity : AppCompatActivity() {

    // Firebase database and store references (there's no need for authentication for this activity)
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var mStorageReference: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // references the references (lol)
        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("menu-items")
        mStorageReference = FirebaseStorage.getInstance().reference

        // This function is used to get the data from the database and analyzes it
        // to the menu recycler view
        val getData = object : ValueEventListener {

            // in case the function fails (in most cases will be due to internet failure)
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MenuActivity, "An issue has occurred when connecting to the database, please check your internet and try again.", Toast.LENGTH_LONG).show()
                Log.e("Error", "DatabaseError", error.toException())
                finish()
            }

            // loads the data into the recycler view and will automaticly update when there
            // is a data change
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
                recycler_view.adapter = adapter
                recycler_view.layoutManager = LinearLayoutManager(this@MenuActivity)
                recycler_view.setHasFixedSize(true)
            }
        }
        mDatabaseRef.addValueEventListener(getData)
        mDatabaseRef.addListenerForSingleValueEvent(getData)
    }

    // In case a dummy list is needed for testing, this will create a recyclerview list to use to test on our menu.
    private fun generateDummyList(size: Int) : List<MenuItem> {
        val list = ArrayList<MenuItem>()

        for (i in 0 until size) {
            val drawable = when (i % 5) {
                0 -> R.drawable.pizza
                1 -> R.drawable.pasta
                2 -> R.drawable.hotdogs
                3 -> R.drawable.hamburger
                else -> R.drawable.salad

            }
            val rndPrice = Random.nextInt(35,100)

            val item = MenuItem (drawable, "פריט $i", "תיאור פריט $i", rndPrice)
            list += item
        }

        return list
    }
}

