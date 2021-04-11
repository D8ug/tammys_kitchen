package com.schoolproject.tammyskitchen

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_new_menu_items.*

class AddNewMenuItemsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var imageUri: Uri

    private val mRequestCode = 420 // le epic number used as my confirmation code :sunglasses:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_menu_items)

        auth = FirebaseAuth.getInstance()
        mStorageReference = FirebaseStorage.getInstance().reference.child("images/")
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        progressBar.visibility = ProgressBar.INVISIBLE

        if (auth.currentUser?.uid != resources.getString(R.string.admin_UID)) finish()

        previewImageView.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, mRequestCode)
        }

        uploadButton.setOnClickListener {
            uploadDataToDatabase()
        }
    }

    private fun uploadDataToDatabase() {
        if (imageUri == null){
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }
        if (nameEditText.text == null){
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show()
            return
        }
        if (priceEditText.text == null){
            Toast.makeText(this, "Please enter an item price", Toast.LENGTH_SHORT).show()
            return
        }
        val currentFileID = System.currentTimeMillis().toString()
        val fileIDWithExtension = currentFileID + "." + getFileExtension(imageUri)

        val itemReference =  mStorageReference.child(fileIDWithExtension!!)
        itemReference.putFile(imageUri).addOnSuccessListener {
            // When the image is successfully uploaded:

            progressBar.visibility = ProgressBar.INVISIBLE
            Toast.makeText(this, "added item to menu!", Toast.LENGTH_SHORT).show()

            itemReference.downloadUrl.addOnSuccessListener {
                val newMenuItem = LiveMenuItem(it.toString(), nameEditText.text.toString(), descriptionEditText.text.toString(), priceEditText.text.toString().toInt(), currentFileID)
                mDatabaseReference.child("menu-items").child(currentFileID).setValue(newMenuItem)
            }


            //finish()

        }.addOnProgressListener {

            // When uploading the image it will show the progress bar hence the following code

            progressBar.visibility = ProgressBar.VISIBLE


        }.addOnFailureListener {

            // If the image upload fails for some reason, it will log the issue and will toast that the uploading failed

            progressBar.visibility = ProgressBar.INVISIBLE
            Log.e("Uploading Error", it.message)
            Toast.makeText(this, "Uploading failed", Toast.LENGTH_LONG).show()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mRequestCode && resultCode == Activity.RESULT_OK && data != null){
            imageUri = data.data!!
            previewImageView.setImageURI(imageUri)
        }
    }

    private fun getFileExtension (uri: Uri) : String? {
        val cr: ContentResolver = contentResolver
        val mime: MimeTypeMap = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uri))
    }
}
