package com.hema.e_commerce

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
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
import com.hema.e_commerce.ui.settings.sharedpreferences.SharedPreferencesProvider
import com.hema.e_commerce.ui.settings.sharedpreferences.SharedPreferencesProvider.Companion.PERMISSION_LOCATION_REQUEST_CODE
import com.vmadalin.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var sharedPref: SharedPreferencesProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        sharedPref = SharedPreferencesProvider(this)
        bindLocation()
        bindNav()

    }

    @SuppressLint("MissingPermission")
    private fun bindLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (hasLocationPermission() && sharedPref.isFirstTimeLaunch) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->

                val geoCoder = Geocoder(this)
                val currentLocation =
                    geoCoder.getFromLocation(location.latitude, location.longitude, 1)

                Log.d("first", currentLocation[0].getAddressLine(0))
                sharedPref.setLocation(
                    location.latitude.toString(),
                    location.longitude.toString(),
                    currentLocation[0].getAddressLine(0),
                )
                // to get my location in first time only
                sharedPref.setFirstTimeLaunch(false)
            }
        } else {
            requestLocationPermission()
        }

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


    private fun bindNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavView.setupWithNavController(navController)

        // to remove bottom nav from this fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.signInFragment || destination.id == R.id.signUpFragment
                || destination.id == R.id.editAddress || destination.id == R.id.mapFragment
                || destination.id == R.id.editProfile
            ) {
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
                R.id.Settings
            )

        )

//        setSupportActionBar(binding.mytoolbar)

    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return true
//    }

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