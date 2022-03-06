package com.hema.e_commerce.ui.settings.map

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentMapBinding
import com.hema.e_commerce.Connectivity
import com.hema.e_commerce.ui.settings.ProfileViewModel


class MapFragment : Fragment() {

    private val connectivity = Connectivity
    private var markerLat: Double = 0.0
    private var markerLong: Double = 0.0
    private lateinit var binding: FragmentMapBinding

    //    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var viewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
//        sharedPref = SharedPreferencesProvider(requireActivity())


        binding.openLocation.setOnClickListener {
            if (markerLat == 0.0 && markerLong == 0.0) {
                return@setOnClickListener
            }
            if (!connectivity.isOnline(this.requireContext())) {
                return@setOnClickListener
            }

//            viewModel.setData(markerLat.toString(), markerLong.toString())
//            sharedPref.setLatLong(markerLat.toString(), markerLong.toString())
            Log.e("locatiomd", markerLat.toString() + "ff" + markerLong.toString())

//            val customLocationMapFragment = HomeFragment()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
//                ?.replace(this.id, customLocationMapFragment)
//                ?.addToBackStack("homeFragment")
//                ?.commit()

        }


        var supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map_location) as SupportMapFragment
        supportMapFragment.getMapAsync { it ->

            var googleMap = it

            it.setOnMapClickListener {
                var markerOptions = MarkerOptions()
                markerOptions.position(it)
                markerLat = it.latitude
                markerLong = it.longitude
                markerOptions.title("${it.latitude} : ${it.longitude}")
                googleMap.clear()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 10F))
                googleMap.addMarker(markerOptions)
            }
<<<<<<< Updated upstream
=======
            it.isMyLocationEnabled = true


>>>>>>> Stashed changes
        }
    }


}