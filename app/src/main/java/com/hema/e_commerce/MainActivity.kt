package com.hema.e_commerce

import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.hema.e_commerce.databinding.ActivityMainBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.MainActivityViewModelFactory
import com.hema.e_commerce.model.viewmodels.MainActivityViewModel
import com.hema.e_commerce.util.Connectivity
import com.hema.e_commerce.util.LocationProvider
import com.hema.e_commerce.util.Network
import com.hema.e_commerce.util.SharedPreferencesProvider

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var locationManager: LocationManager
    private val locationProvider = LocationProvider
    private val connectivity = Connectivity
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var sharedPref: SharedPreferencesProvider

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreferencesProvider(this)
       /* val networkConnection = Network(applicationContext)
        networkConnection.observe(this) { isConnect ->
            if (isConnect) {
                binding.fragmentContainerView.visibility = View.VISIBLE
//                binding.bottomNavView.visibility = View.VISIBLE
                binding.ivWifi.visibility = View.GONE
                binding.tvInternetConnection.visibility = View.GONE

            } else {
                binding.fragmentContainerView.visibility = View.GONE
//                binding.bottomNavView.visibility = View.GONE
                binding.ivWifi.visibility = View.VISIBLE
                binding.tvInternetConnection.visibility = View.VISIBLE
                Toast.makeText(this, "Connection Field", Toast.LENGTH_SHORT).show()

            }
        }
*/

        val repository = Repository(RoomData(applicationContext))
        val mainViewModelProviderFactory = MainActivityViewModelFactory(application, repository)
        viewModel =
            ViewModelProvider(this, mainViewModelProviderFactory)[MainActivityViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        bindLocation()
        bindNav()

        if (sharedPref.isSignIn) {
            cartIconBadge()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindLocation() {
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        if (connectivity.isOnline(this)) {
            openGPS()
            locationProvider.initLocation(this)
            locationProvider.getDeviceLocation()
        } else {
            Toast.makeText(this, R.string.offline, Toast.LENGTH_LONG).show()
        }
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
                || destination.id == R.id.editProfile || destination.id == R.id.searchFragment
                || destination.id == R.id.splashFragment || destination.id == R.id.viewPagerFragment
                || destination.id == R.id.checkout || destination.id == R.id.orderFragment
                || destination.id == R.id.cancelledOrderFragment
                || destination.id == R.id.addAddressFragment || destination.id == R.id.selectAddress

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
    }

    override fun onBackPressed() {
        navController.navigateUp()
    }

    private fun openGPS() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return
        } else {
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setMessage(R.string.gpsOff)
                .setPositiveButton(
                    R.string.ok
                ) { _: DialogInterface?, _: Int ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
                .create()
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationProvider.onRequestPermissionsResult(
            requestCode = requestCode,
            permissions = permissions,
            grantResults = grantResults
        )
    }

    lateinit var cartBadge: BadgeDrawable

    @RequiresApi(Build.VERSION_CODES.M)
    fun cartIconBadge() {
        cartBadge = binding.bottomNavView.getOrCreateBadge(R.id.cart)
        cartBadge.badgeTextColor = getColor(R.color.myRed)
        cartBadge.maxCharacterCount = 5000
        cartBadge.badgeTextColor = getColor(R.color.white)

        viewModel.getCartProducts(sharedPref.getUserInfo().customer?.customerId!!)
            .observe(this, Observer {
                cartBadge.number = it.size
                cartBadge.isVisible = it.isNotEmpty()

            })
    }

    fun clearCartIconBadge() {
        cartBadge = binding.bottomNavView.getOrCreateBadge(R.id.cart)
        cartBadge.isVisible = false

    }


}