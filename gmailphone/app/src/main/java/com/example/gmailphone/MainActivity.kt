package com.example.gmailphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.

class MainActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}