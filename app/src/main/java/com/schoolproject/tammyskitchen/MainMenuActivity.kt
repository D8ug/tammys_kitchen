package com.schoolproject.tammyskitchen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.lang.Exception

class MainMenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        try {
            auth = FirebaseAuth.getInstance()
        }
        catch (exception: Exception){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        if (auth == null){
            // if guest is logged in - TODO: add usability for guests
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        else if (auth.currentUser?.email == resources.getString(R.string.admin_email)) {
            // if admin is logged in, intent to the admin menu
            startActivity(Intent(this, AdminMenuActivity::class.java))
            finish()
        }

        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
