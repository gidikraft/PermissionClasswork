package com.example.mondayclasswork

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mondayclasswork.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val PERMISSION_REQUEST_CAMERA = 0
    }
    private val permissions = arrayOf(Manifest.permission.CAMERA)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resetBtn.setOnClickListener { checkCameraPermission() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binding.mainContainer.showSnackbar(R.string.permission_granted, Snackbar.LENGTH_SHORT)
                startCamera()
            } else {
                // Permission request was denied.
                binding.mainContainer.showSnackbar(R.string.permission_denied, Snackbar.LENGTH_LONG)
            }
        }
    }

    private fun checkCameraPermission() {
        // Check if the storage permission has been granted
        if (checkPermission(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted
            startCamera()
        } else {
            //Requested permission.
            requestCameraPermission()
        }
    }
    private fun requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (shouldRequestPermissionRationale(Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            binding.mainContainer.showSnackbar(R.string.camera_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok
            ) {
                requestAllPermissions(permissions, PERMISSION_REQUEST_CAMERA)
            }
        } else {
            // Request the permission with array.
            requestAllPermissions(permissions, PERMISSION_REQUEST_CAMERA)
        }
    }
    private fun startCamera() {
        // do download stuff here
        binding.mainContainer.showSnackbar(R.string.camera, Snackbar.LENGTH_LONG)
    }
}