package com.submission.dicodingstory.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.submission.dicodingstory.R
import com.submission.dicodingstory.databinding.FragmentGoogleMapsBinding
import com.submission.dicodingstory.util.bitmapFromVector
import com.submission.dicodingstory.vm.StoryVM
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GoogleMapsFragment : Fragment(), OnMapReadyCallback
{
    private var _binding: FragmentGoogleMapsBinding? = null
    private val binding get() = _binding!!
    private val vm: StoryVM by viewModels()
    private lateinit var gmaps: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View
    {
        _binding = FragmentGoogleMapsBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.googleMaps) as SupportMapFragment?
        mapFragment?.getMapAsync(this)}


    override fun onMapReady(googleMap: GoogleMap) {
        gmaps = googleMap
        gmaps.uiSettings.isIndoorLevelPickerEnabled = true
        gmaps.uiSettings.isCompassEnabled = true
        observableViewModel()
        getLoc()}

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    )
    { isGranted ->
        if (isGranted)
            getLoc()}

    private fun observableViewModel()
    {
        vm.getStoryLoc().observe(viewLifecycleOwner)
        {
                res ->
            res.data?.listStory?.forEach{
                    story ->
                Timber.d("$story")
                if (story.lat != null && story.lon != null){
                    val latLng = LatLng(story.lat, story.lon)
                    gmaps.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(story.name)
                            .icon(bitmapFromVector(requireContext(), R.drawable.google_maps_mark)))
                    gmaps.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng, 5F))

                }}}
    }

    private fun getLoc()
    {
        if
                (ContextCompat.checkSelfPermission
                (requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)
        {
            gmaps.isMyLocationEnabled = true
        }
        else
        {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)}
    }
}