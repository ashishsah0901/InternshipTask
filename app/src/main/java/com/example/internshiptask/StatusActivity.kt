package com.example.internshiptask

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.internshiptask.databinding.ActivityStatusBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBinding

    private var isUser: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isUser = intent.getBooleanExtra("isUserLoggedIn", false)
        updateUI(isUser)
        if(!isUser){
            subscribeToObservers()
        } else {
            val reference = FirebaseDatabase.getInstance().reference
            reference.child("isUserLoggedIn").setValue(true)
        }
    }

    private fun subscribeToObservers() {
        FirebaseDatabase.getInstance().reference.child("isUserLoggedIn")
            .addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value == true){
                    binding.statusColor.setBackgroundResource(R.drawable.image_design_green)
                }else{
                    binding.statusColor.setBackgroundResource(R.drawable.image_design_gray)
                }
            }

            override fun onCancelled(error: DatabaseError) = Unit

        })
    }

    private fun updateUI(isUerLoggedIn: Boolean) {
        if(isUerLoggedIn) {
            binding.statusLL.isVisible = false
            binding.welcomeMessage.text = "WELCOME USER"
        } else {
            binding.statusLL.isVisible = true
            binding.welcomeMessage.text = "WELCOME ADMIN"
            FirebaseDatabase.getInstance().reference.child("isUserLoggedIn").get()
                .addOnSuccessListener {
                    if (it.value == true) {
                        binding.statusColor.setBackgroundResource(R.drawable.image_design_green)
                    } else {
                        binding.statusColor.setBackgroundResource(R.drawable.image_design_gray)
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isUser) FirebaseDatabase.getInstance().reference.child("isUserLoggedIn").setValue(false)
    }
}