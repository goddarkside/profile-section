package com.android.pransh.profilesection

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        val backbtn : ImageView = findViewById(R.id.backbtn)
        val editbtn : ImageView = findViewById(R.id.editbtn)
        val credgarage : ImageView = findViewById(R.id.credgarage)
        val creditscore : ImageView = findViewById(R.id.creditscore)
        val caseback : ImageView = findViewById(R.id.caseback)
        val bankbalance : ImageView = findViewById(R.id.bankbalance)
        val casebackbalance : ImageView = findViewById(R.id.casebackbalance)
        val coins : ImageView = findViewById(R.id.coins)
        val refer : ImageView = findViewById(R.id.refer)
        val profilepic : ImageView = findViewById(R.id.profilepic)
        val name : TextView = findViewById(R.id.name)
        val alltransactions : ImageView = findViewById(R.id.alltransactions)
        val supportbtn : LinearLayout = findViewById(R.id.supportbtn)

        backbtn.setOnClickListener {
            Toast.makeText(this, "Back Button Clicked", Toast.LENGTH_SHORT).show()
        }
        supportbtn.setOnClickListener {
            Toast.makeText(this, "Support Button Clicked", Toast.LENGTH_SHORT).show()
        }
        editbtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        credgarage.setOnClickListener {
            Toast.makeText(this, "CRED Garage Clicked", Toast.LENGTH_SHORT).show()
        }
        creditscore.setOnClickListener {
            Toast.makeText(this, "Credit Score Clicked", Toast.LENGTH_SHORT).show()
        }
        caseback.setOnClickListener {
            Toast.makeText(this, "Caseback Clicked", Toast.LENGTH_SHORT).show()
        }
        bankbalance.setOnClickListener {
            Toast.makeText(this, "Bank Balance Clicked", Toast.LENGTH_SHORT).show()
        }
        casebackbalance.setOnClickListener {
            Toast.makeText(this, "Caseback Balance Clicked", Toast.LENGTH_SHORT).show()
        }
        coins.setOnClickListener {
            Toast.makeText(this, "Coins Clicked", Toast.LENGTH_SHORT).show()
        }
        refer.setOnClickListener {
            Toast.makeText(this, "Refer To Earn Clicked", Toast.LENGTH_SHORT).show()
        }
        alltransactions.setOnClickListener {
            Toast.makeText(this, "All Transactions Clicked", Toast.LENGTH_SHORT).show()
        }

        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        name.text = sharedPref.getString("name", "andaz Kumar")
//        val gender = sharedPref.getString("gender", "Male")
// (Optional) Display gender
        val imageUri = sharedPref.getString("profileImageUri", null)
        if (imageUri != null) {
            findViewById<ImageView>(R.id.profilepic).setImageURI(Uri.parse(imageUri))
        }


    }
}