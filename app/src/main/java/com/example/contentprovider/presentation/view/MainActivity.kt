package com.example.contentprovider.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.contentprovider.data.MediaRepo
import com.example.contentprovider.databinding.ActivityMainBinding
import com.example.contentprovider.presentation.adapter.ImageMediaGridAdapter
import com.example.contentprovider.presentation.viewmodel.ImageMediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel:ImageMediaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        askPermissionAndProceed()

        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("rahul", "onCreate: ")
            val timeTaken = measureTimeMillis {
              val b =  async {
                    delay(2000) // 2 seconds
                }

                delay(1000) // 1 second

               val a =  async {
                    withContext(Dispatchers.IO) {
                        delay(2000) // 2 seconds
                    }
                }
                a.await()
                b.await()

            }

            println("Total time taken: $timeTaken ms")
        }
    }


    private fun loadMediaImages() {
        val repo = MediaRepo(this)
        val adapter = ImageMediaGridAdapter(this)
        binding.gridView.adapter = adapter

        viewModel.mediaFiles.observe(this) { files ->
            Log.d("rahul", "onCreate: $files")
            adapter.setData(files)
        }

        viewModel.loadMedia(repo)
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {

            loadMediaImages()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askPermissionAndProceed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED -> {

                    loadMediaImages()
                }

                else -> {

                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
        } else {

            loadMediaImages()
        }
    }

}