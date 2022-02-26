package com.hema.e_commerce

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hema.e_commerce.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (hasLocationPermission()) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
                Log.d("first",location.latitude.toString())
                Log.d("first",location.longitude.toString())
            }
        } else {
            requestLocationPermission()
        }

        setNav()

    }

    // this fun return true if our app has this ACCESS_FINE_LOCATION
    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this, "this app can 't work without this permission",
            PERMISSION_LOCATION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


    private fun setNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.signInFragment || destination.id == R.id.signUpFragment) {
                binding.bottomNavView.visibility = View.GONE
            } else {
                binding.bottomNavView.visibility =
                    View.VISIBLE
            }

        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home,
                R.id.category,
                R.id.cart,
                R.id.profile
            )

        )
    }

    override fun onBackPressed() {
        navController.navigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d("Dddd", "onPermissionsGranted:" + requestCode + ":" + perms.size)
        requestLocationPermission()

    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show()
    }

}