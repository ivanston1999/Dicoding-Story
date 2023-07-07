package com.submission.dicodingstory.dashboard


import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.submission.dicodingstory.R
import com.submission.dicodingstory.error.OperationStatus
import com.submission.dicodingstory.req.StoryReq
import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT

import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.submission.dicodingstory.databinding.FragmentNewStoryBinding
import com.submission.dicodingstory.util.*
import com.submission.dicodingstory.util.Default.CAMERA_PERMISSIONS
import com.submission.dicodingstory.vm.StoryVM
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

import android.view.LayoutInflater
import android.view.View

@AndroidEntryPoint
class NewStoryFragment : Fragment() {
    private var _binding: FragmentNewStoryBinding? = null
    private val binding get() = _binding!!
    private var fm: File? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat: Float? = null
    private var lon: Float? = null
    private val vm: StoryVM by viewModels()
    @Inject
    lateinit var loadAnim: LoadingAnimation

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Default.CODE_PERMISSIONS) {
            if (!areAllPermissionsGranted()) {
                view?.mySnackBar("There is no permission!")
                requireActivity().finish()
            }
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!areAllPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                CAMERA_PERMISSIONS,
                Default.CODE_PERMISSIONS
            )
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.shareLoc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                launchLocationPermission()
            } else {
                lat = null
                lon = null
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController()
                .navigateUp() }
        binding.btnCamera.setOnClickListener {
            takePicture() }
        binding.btnGallery.setOnClickListener {
            openFileManager() }
        binding.buttonAdd.setOnClickListener {
            performUpload() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun takePicture() {
        val goTO = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager = requireContext().packageManager
        val resolvedActivity = goTO.resolveActivity(packageManager)

        generateCustomTempFile(requireContext().applicationContext).also { customFile ->
            val photoURI = FileProvider.getUriForFile(
                requireContext(), "com.submission.dicodingstory", customFile
            )
            currentPhotoPath = customFile.absolutePath
            goTO.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launchCameraIntent.launch(goTO)
        }
    }


    private fun getLastKnownLocation() {
        if (checkPermission(Default.LOC_PERMISSION[0]) && checkPermission(Default.LOC_PERMISSION[1])) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    lat = location.latitude.toFloat()
                    lon = location.longitude.toFloat()
                }
            }
        } else {
            launchLocationPermission()
        }
    }



    private fun performUpload() {
        val desc = binding.edAddDescription.texTrim()
        val req = StoryReq(desc, lat = lat, lon = lon)

        if (desc.isEmpty()) {
            view?.mySnackBar("${getString(R.string.description)} ${getString(R.string.required)}")
        } else if (fm == null) {
            view?.mySnackBar("${getString(R.string.img)} ${getString(R.string.required)}")
        } else {
            fm?.let { observeViewModel(req, it) }
        }
    }




    private fun openFileManager() {
        val goTo = Intent()
        goTo.action = ACTION_GET_CONTENT
        goTo.type = "image/*"
        val chooserIntent = Intent.createChooser(goTo, "Choose image")
        launchGalleryIntent.launch(chooserIntent)
    }




    fun observeViewModel(request: StoryReq, file: File) {
        vm.uploadStory(request, file).observe(viewLifecycleOwner) { res ->
            when (res.status) {
                OperationStatus.LOADING -> loadAnim.start(requireContext())
                OperationStatus.ERROR -> {
                    loadAnim.stop()
                    view?.mySnackBar(res.message.toString())
                }
                OperationStatus.SUCCESS -> {
                    loadAnim.stop()
                    view?.mySnackBar(res.data?.message.toString())
                    findNavController().navigateUp()
                }
            }
        }
    }

    private val launchGalleryIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val image: Uri = result.data?.data as Uri
            val myFile = toFile(image, requireContext())
            this.fm = myFile
            binding.imgPicture.setImageURI(image)
            binding.imgPicture.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }



    private fun launchLocationPermission() {
        locationPermissionLauncher.launch(Default.LOC_PERMISSION)
    }

    private lateinit var currentPhotoPath: String
    private val launchCameraIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            this.fm = myFile

            val bitmap = BitmapFactory.decodeFile(myFile.path)
            binding.imgPicture.setImageBitmap(bitmap)
            binding.imgPicture.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private var locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when
            {permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?:
            false -> getLastKnownLocation()

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?:
                false -> getLastKnownLocation()
                else ->
                {binding.shareLoc.isChecked = false
                    binding.shareLoc.isEnabled = false}}}

    private fun areAllPermissionsGranted() = CAMERA_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }





    private fun checkPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
