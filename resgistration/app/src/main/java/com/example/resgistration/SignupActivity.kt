package com.example.resgistration

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    companion object {
        const val TAG = "TAG"
    }

    private lateinit var mFullName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mPhone: EditText
    private lateinit var mRegisterBtn: Button
    private lateinit var mLoginBtn: TextView
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mFullName = findViewById(R.id.fullName)
        mEmail = findViewById(R.id.Email)
        mPassword = findViewById(R.id.password)
        mPhone = findViewById(R.id.phone)
        mRegisterBtn = findViewById(R.id.registerBtn)
        mLoginBtn = findViewById(R.id.createText)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        progressBar = findViewById(R.id.progressBar)

        if (fAuth.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        mRegisterBtn.setOnClickListener {
            val email = mEmail.text.toString().trim()
            val password = mPassword.text.toString().trim()
            val fullName = mFullName.text.toString()
            val phone = mPhone.text.toString()

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.")
                return@setOnClickListener
            }

            if (password.length < 6) {
                mPassword.setError("Password Must be >= 6 Characters")
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            // register the user in firebase
            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (task.isSuccessful) {

                        // send verification link
                        val fuser: FirebaseUser? = fAuth.currentUser
                        fuser?.sendEmailVerification()?.addOnSuccessListener(OnSuccessListener {
                            Toast.makeText(this@Register, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show()
                        })?.addOnFailureListener(OnFailureListener { e ->
                            Log.d(TAG, "onFailure: Email not sent $e.message")
                        })

                        Toast.makeText(this@Register, "User Created.", Toast.LENGTH_SHORT).show()
                        userID = fAuth.currentUser?.uid ?: ""
                        val documentReference: DocumentReference = fStore.collection("users").document(userID)
                        val user: MutableMap<String, Any> = HashMap()
                        user["fName"] = fullName
                        user["email"] = email
                        user["phone"] = phone
                        documentReference.set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "onSuccess: user Profile is created for $userID")
                            }
                            .addOnFailureListener { e ->
                                Log.d(TAG, "onFailure: $e")
                            }
                        startActivity(Intent(applicationContext, MainActivity::class.java))

                    } else {
                        Toast.makeText(this@Register, "Error ! ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                })
        }

        mLoginBtn.setOnClickListener {
            startActivity(Intent(applicationContext, Login::class.java))
        }
    }
}
