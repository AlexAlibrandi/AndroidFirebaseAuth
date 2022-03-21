package com.android.capstone

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.capstone.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.already.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotpassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    fun login(view: View) {

        when {
            TextUtils.isEmpty(binding.emailLog.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this@LoginActivity, "Please enter email.", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(binding.passwordLog.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this@LoginActivity, "Please enter password.", Toast.LENGTH_SHORT)
                    .show()
            }

            else -> {

                auth.signInWithEmailAndPassword(binding.emailLog.text.toString(), binding.passwordLog.text.toString())
                    .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser? = task.result?.user
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        if (firebaseUser != null) {
                            intent.putExtra("user_id", firebaseUser.uid)
                        }
                        intent.putExtra("email_id", binding.emailLog.text.toString())
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}