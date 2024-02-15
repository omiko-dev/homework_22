package com.example.homework_22.presentation.screen

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.homework_22.R
import com.example.homework_22.databinding.ActivityMainBinding
import com.example.homework_22.presentation.screen.home.HomeFragmentDirections
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) {}
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readPushToken()

        if (intent.action == "OPEN_FRAGMENT") {
            val fragmentOpenId = intent.getStringExtra("POST_ID")
            val navHostFragment = supportFragmentManager.findFragmentById(
                R.id.fragmentContainerView
            ) as NavHostFragment
            val action = HomeFragmentDirections.actionHomeFragmentToPostFragment(
                fragmentOpenId?.toInt() ?: 0
            )
            navHostFragment.findNavController().navigate(action)
        }
    }


    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }


    private fun readPushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("omiko", task.exception?.message ?: "")
                return@OnCompleteListener
            }

            val token = task.result
            Log.i("omiko", token)
        })
    }
}