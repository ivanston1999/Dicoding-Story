package com.submission.dicodingstory.util

import android.Manifest

object Default {
    const val UNCERTAIN = "Uncertain eror, just try again"
    const val TIMEOUT = "Try Again"
    val CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    const val CODE_PERMISSIONS = 10
    const val STARTPAGE = 1
    var BASEURL_MOCK: String? = null
    val LOC_PERMISSION = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)


}