package com.example.internshiptask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.internshiptask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginAsAdmin.setOnClickListener {
            Intent(this, StatusActivity::class.java).apply {
                putExtra("isUserLoggedIn", false)
                startActivity(this)
            }
        }
        binding.loginAsUser.setOnClickListener {
            Intent(this, StatusActivity::class.java).apply {
                putExtra("isUserLoggedIn", true)
                startActivity(this)
            }
        }
    }
}