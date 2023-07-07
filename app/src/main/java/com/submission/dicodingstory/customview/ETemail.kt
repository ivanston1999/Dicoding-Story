package com.submission.dicodingstory.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import com.submission.dicodingstory.R

class ETemail : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(context)
    }

    private fun init(context: Context) {
        background = null
        hint = context.getString(R.string.your_email)

        doAfterTextChanged {
            if (!Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                error = context.getString(R.string.wrong_email)
            } else {
                error = null
            }
        }
    }
}
