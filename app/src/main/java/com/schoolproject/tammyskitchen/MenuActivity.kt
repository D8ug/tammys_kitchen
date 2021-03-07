package com.schoolproject.tammyskitchen

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.menu_item.view.*


class MenuActivity : AppCompatActivity() {

    private lateinit var mStorageRef: DatabaseReference
    var storage = FirebaseStorage.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        mStorageRef = FirebaseDatabase.getInstance().reference.child("menu_items")
        val list = ArrayList<LiveMenuItem>()
        val lemonImageURL = "https://firebasestorage.googleapis.com/v0/b/tammy-s-kitchen.appspot.com/o/images%2Flemon.png?alt=media&token=a1f173f7-5508-4dbf-8612-ed804767f63a"

        val info = FirebaseRecyclerOptions.Builder<LiveMenuItem>().setQuery(mStorageRef, LiveMenuItem::class.java).build()
        val testref = storage.reference.child("images/")
        var test = testref.listAll()
        

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        /* - used for testing my list
        val testList = generateDummyList(100)

        recycler_view.adapter = MenuAdapter(testList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

         */

    }

    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        try {
            var storageRef = storage.reference.child("images")



        }
        catch (e: Exception){
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MenuActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

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

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val imageView: ImageView = itemView.image_view
    val itemName: TextView = itemView.item_name_text_view
    val itemDescription: TextView = itemView.description_text_view
    val itemPrice: TextView = itemView.price_text_view
}