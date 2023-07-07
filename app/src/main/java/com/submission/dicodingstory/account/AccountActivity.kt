package com.submission.dicodingstory.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.submission.dicodingstory.databinding.ActivityAccountBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }


}