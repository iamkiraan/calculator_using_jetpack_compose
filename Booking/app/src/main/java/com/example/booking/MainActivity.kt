package com.example.booking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var userTypeDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showUserTypeDialog()
    }

    private fun showUserTypeDialog() {
        val userTypeOptions = arrayOf("Owner", "User")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select User Type")

        builder.setPositiveButton(userTypeOptions[0]) { _, _ ->
            val intent = Intent(this,OwnerActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(userTypeOptions[1]) { _, _ ->
            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
        }

        userTypeDialog = builder.create()
        userTypeDialog.show()
    }


}

