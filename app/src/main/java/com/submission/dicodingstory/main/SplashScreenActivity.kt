package com.submission.dicodingstory.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.submission.dicodingstory.account.AccountActivity
import com.submission.dicodingstory.databinding.ActivitySplashScreenBinding
import com.submission.dicodingstory.util.AccountPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    @Inject
    lateinit var pref: AccountPref
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed(
            {
            if (pref.getSignIn())
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
            else
            {

                startActivity(Intent(this, AccountActivity::class.java))
                finish()
            }
        }, 1500)}
}