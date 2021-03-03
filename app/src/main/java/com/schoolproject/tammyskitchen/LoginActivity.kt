package com.schoolproject.tammyskitchen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity() : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }

        loginButton.setOnClickListener {
            loginUser()
        }

        guestButton.setOnClickListener {
            loginGuest()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    fun loginGuest(){
        auth.signInWithEmailAndPassword(resources.getString(R.string.guest_email), resources.getString(R.string.guest_password))
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("guest sign in", "signInAsGuest:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Welcome back${if (auth.currentUser?.displayName== null) "!" else ", ${auth.currentUser?.displayName}"}",
                        Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {

                    Log.w("guest sign in", "signInAsGuest:failure", task.exception)
                    Toast.makeText(baseContext, "An error was issued. Try checking your internet connection.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }

            }
        reload()
    }

    fun loginUser(){
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            emailEditText.requestFocus()
            return
        }

        if (password.isEmpty()){
            passwordEditText.error = "Please enter a password"
            passwordEditText.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("sign in", "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Welcome back${if (auth.currentUser?.displayName== null) "!" else ", ${auth.currentUser?.displayName}"}",
                        Toast.LENGTH_SHORT).show()
                    updateUI(user)
                } else {

                    Log.w("sign in", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }

            }
    }


    fun updateUI(user: FirebaseUser?){
        if (user == null) {
            // If still not connected to a user
            return
        }
        reload()
    }


    fun reload(){
        if (auth.currentUser == null) {
            // will log for the console in case reload() gets called but no user is connected
            // although, currently it should never get to this part of code if used correctly
            Log.d("reload", "Tried to reload with a user, yet not user is connected")
            return
        }
        val intent: Intent
        if (auth.currentUser?.email == resources.getString(R.string.admin_email))
            intent = Intent(this, AdminMenuActivity::class.java)
        else
            intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

}
