package com.rajyadavnp.oppia.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ListView

class RajExpandedListView : ListView {
    var widthSpec: Int = 0
    var heightSpec: Int = 0

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(View.MEASURED_SIZE_MASK, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
        widthSpec = widthMeasureSpec
        heightSpec = expandSpec
        setMeasuredDimension(measuredWidth, measuredHeight + 500)
    }
}