package com.submission.dicodingstory.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.submission.dicodingstory.R

class ETpassword : AppCompatEditText {

    private var password = 0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        background = null
        hint = context.getString(R.string.your_password)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password = p1 + p3
                error = if (password < 8) context.resources.getString(R.string.password_error) else null
            }

            override fun afterTextChanged(p0: Editable?) {}})}

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)}
}
