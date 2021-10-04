package com.example.kelompoksatuuser.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.kelompoksatuuser.R
import com.example.kelompoksatuuser.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.imgSplash.alpha = 0f
        binding.imgSplash.animate().setDuration(1500).alpha(1f).withEndAction {
            Handler().postDelayed({
                    val login = Intent(this, LoginActivity::class.java)
                    startActivity(login)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
            },2000)
        }
    }
}