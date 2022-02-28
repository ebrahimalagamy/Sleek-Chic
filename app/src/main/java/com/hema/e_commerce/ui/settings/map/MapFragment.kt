package com.hema.e_commerce.ui.settings.map

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hema.e_commerce.Connectivity
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentMapBinding
import com.hema.e_commerce.ui.settings.sharedpreferences.SharedPreferencesProvider


class MapFragment : Fragment() {

    private val connectivity = Connectivity
    private var markerLat: Double = 0.0
    private var markerLong: Double = 0.0
    private lateinit var address: String
    private lateinit var binding: FragmentMapBinding
    private lateinit var sharedPref: SharedPreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())

        binding.btnLocation.setOnClickListener {
            if (markerLat == 0.0 && markerLong == 0.0) {
                return@setOnClickListener
            }
            if (!connectivity.isOnline(this.requireContext())) {
                return@setOnClickListener
            }
            sharedPref.setLocation(markerLat.toString(), markerLong.toString(), address)
            findNavController().navigate(R.id.action_mapFragment_to_editAddress)

        }


        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map_location) as SupportMapFragment
        supportMapFragment.getMapAsync { it ->

            var googleMap = it

//            val latlng = LatLng(
//                sharedPref.latLong[1]?.toDouble() ?: 30.033333,
//                sharedPref.latLong[2]?.toDouble() ?:31.233334
//            )
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 20F))


            it.setOnMapClickListener {
                var markerOptions = MarkerOptions()
                markerOptions.position(it)
                markerLat = it.latitude
                markerLong = it.longitude
                markerOptions.title("${it.latitude} : ${it.longitude}")
                val geoCoder = Geocoder(requireActivity())
                val currentLocation =
                    geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                address = currentLocation[0].getAddressLine(0)
                Log.d("Address", currentLocation[0].getAddressLine(0))

                googleMap.clear()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                googleMap.addMarker(markerOptions)


            }

//            binding.relocate.setOnClickListener {
//                it.isMyLocationEnabled = true
//
////                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(googleMap, 10F))
//
//            }
//            it.setOnMyLocationButtonClickListener {
//                it.isMyLocationEnabled = true
//                var markerOptions = MarkerOptions()
//            }
            it.isMyLocationEnabled = true


        }
    }


}
//postalCode = addresses.get(0).getPostalCode();
//city = addresses.get(0).getLocality();
//address = addresses.get(0).getAddressLine(0);
//state = addresses.get(0).getAdminArea();
//country = addresses.get(0).getCountryName();
//knownName = addresses.get(0).getFeatureName();
//Log.e("Location",postalCode+" "+city+" "+address+" "+state+" "+knownName);