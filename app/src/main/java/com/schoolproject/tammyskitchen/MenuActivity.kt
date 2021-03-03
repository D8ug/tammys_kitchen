package com.schoolproject.tammyskitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.random.Random

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val testList = generateDummyList(100)

        recycler_view.adapter = MenuAdapter(testList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

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
