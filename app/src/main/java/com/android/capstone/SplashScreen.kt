package com.android.capstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
<<<<<<< HEAD
import com.android.chitchat.R
import com.android.chitchat.databinding.ActivitySplashScreenBinding
=======
import com.android.capstone.databinding.ActivitySplashScreenBinding
>>>>>>> 6f928b4fc94673b0e245d36c5fca99ca09088a48

class SplashScreen : AppCompatActivity() {
    lateinit var mainBinding: ActivitySplashScreenBinding
    lateinit var topAnimation: Animation
    lateinit var bottomAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mainBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)


        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        mainBinding.image.animation = topAnimation
        mainBinding.welcome.animation = bottomAnimation
        mainBinding.slogan.animation = bottomAnimation

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}