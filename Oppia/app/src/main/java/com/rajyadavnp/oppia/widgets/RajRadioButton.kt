package com.rajyadavnp.oppia.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.RadioButton
import com.rajyadavnp.oppia.Oppia

class RajRadioButton : RadioButton {
    constructor(context: Context) : super(context) {
        setFont(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setFont(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setFont(context)
    }

    private fun setFont(context: Context) {
        when {
            this.typeface.style == Typeface.BOLD_ITALIC -> {
                typeface = Oppia.BOLD_ITALIC
            }
            this.typeface.style == Typeface.BOLD -> {
                typeface = Oppia.BOLD
            }
            this.typeface.style == Typeface.ITALIC -> {
                typeface = Oppia.ITALIC
            }
            else -> {
                typeface = Oppia.REGULAR
            }
        }
    }
}