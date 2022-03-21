package com.android.capstone

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.capstone.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private lateinit var binding : ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.already.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun register(view: View) {

        when {
            TextUtils.isEmpty(binding.emailReg.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please enter email.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(binding.passReg.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this@RegisterActivity,
                    "Please enter password.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(binding.confirmPass.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please confirm password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            !TextUtils.equals(binding.passReg.text.toString(), binding.confirmPass.text.toString()) -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Passwords do not match.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {

            val intent = Intent(this, MainActivity::class.java)

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                binding.emailReg.text.toString().trim(){it <= ' '},
                binding.passReg.text.toString().trim(){it <= ' '})
                .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val firebaseUser: FirebaseUser? = task.result?.user
                    Toast.makeText(this, "registration successful", Toast.LENGTH_SHORT).show()
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    if (firebaseUser != null) {
                        intent.putExtra("user_id", firebaseUser.uid)
                    }
                    intent.putExtra("email_id", binding.emailReg.text.toString())
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    }
}