package com.schoolproject.tammyskitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_expense.*

class NewExpenseActivity : AppCompatActivity() {

    lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        mDatabaseReference = FirebaseDatabase.getInstance().reference.child(resources.getString(R.string.expenses_path))

        newExpenseButton.setOnClickListener {
            val errorToast = Toast.makeText(this, "An error has occured", Toast.LENGTH_SHORT)
            if (expenseAmountEditText.text.toString().toDoubleOrNull() == null) {
                errorToast.show()
                return@setOnClickListener
            }
            if (expenseNameEditText.text.toString() == ""){
                errorToast.show()
                return@setOnClickListener
            }
            val UID = createUID()
            val newExpense : IncomeItem = IncomeItem(expenseAmountEditText.text.toString().toDouble(),
                                                     expenseNameEditText.text.toString(),
                                                     expenseDescriptionEditText.text.toString(),
                                                     UID,
                                                     expenseDetailsEditText.text.toString())
            mDatabaseReference.child(UID).setValue(newExpense)
        }

    }

    private fun createUID(): String {
        return System.currentTimeMillis().toString()
    }
}
