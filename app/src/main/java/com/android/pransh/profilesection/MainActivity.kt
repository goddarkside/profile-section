package com.android.pransh.profilesection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest


class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "user_prefs"
    private val PICK_IMAGE = 1
    private val PERMISSION_REQUEST = 100
    private var selectedImageUri: Uri? = null

    private lateinit var profileImage: ImageView
    private lateinit var editName: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var removePhoto: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        genderSpinner = findViewById(R.id.genderSpinner)
        editName = findViewById(R.id.editName)
        saveButton = findViewById(R.id.btnSave)
        profileImage = findViewById(R.id.editProfileImage)
        removePhoto = findViewById(R.id.btnRemovePhoto)
        // Setup Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        loadData()

        profileImage.setOnClickListener {
            checkPermissionAndPickImage()
        }

        removePhoto.setOnClickListener {
            selectedImageUri = null
            profileImage.setImageResource(R.drawable.profileimg)
        }

        saveButton.setOnClickListener {
            saveData()
            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun saveData() {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("name", editName.text.toString())
            putString("gender", genderSpinner.selectedItem.toString())
            putString("profileImageUri", selectedImageUri?.toString())
            apply()
        }
    }

    private fun loadData() {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editName.setText(sharedPref.getString("name", "Andaz Kumar"))
        val gender = sharedPref.getString("gender", "Male")
        genderSpinner.setSelection(
            resources.getStringArray(R.array.gender_array).indexOf(gender)
        )

        val imageUri = sharedPref.getString("profileImageUri", null)
        if (imageUri != null) {
            profileImage.setImageURI(Uri.parse(imageUri))
        } else {
            profileImage.setImageResource(R.drawable.profileimg)
        }
    }

    private fun checkPermissionAndPickImage() {
        val requiredPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                this,
                requiredPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(requiredPermission), PERMISSION_REQUEST)
        } else {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            profileImage.setImageURI(selectedImageUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImageFromGallery()
        } else {
            Toast.makeText(this, "Permission denied to access media", Toast.LENGTH_SHORT).show()
        }
    }
}