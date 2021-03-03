package com.schoolproject.tammyskitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)



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

        }

        return list
    }
}
