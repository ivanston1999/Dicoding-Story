package com.submission.dicodingstory.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.submission.dicodingstory.R

class LoadingAnimation {

    private var dialog: Dialog? = null

    fun start(context: Context)
    {
        dialog = setDialog(context)}

    fun stop()
    {
        if (dialog?.isShowing == true) dialog?.cancel()}

    private fun setDialog(context: Context): Dialog? {

        dialog = Dialog(context)

        dialog.let {
            it?.show()
            it?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it?.setContentView(R.layout.loading_anime)
            it?.setCancelable(false)
            it?.setCanceledOnTouchOutside(false)
            return it}}

}