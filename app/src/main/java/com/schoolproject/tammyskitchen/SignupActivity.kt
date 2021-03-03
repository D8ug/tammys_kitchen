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

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        signupButton.setOnClickListener {
            signupUser()
        }

        loginButton.setOnClickListener {
            finish()
        }

        guestButton.setOnClickListener {
            loginGuest()
        }
    }

    fun signupUser(){
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

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Created an account.",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

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
        updateUI(auth.currentUser)
    }

    fun updateUI(user: FirebaseUser?){
        if (user == null) return
        val intent = Intent(this, MainMenuActivity::class.java)
        Toast.makeText(baseContext, "Welcome${if (auth.currentUser?.displayName== null) "!" else ", ${auth.currentUser?.displayName}"}",
            Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }


}
