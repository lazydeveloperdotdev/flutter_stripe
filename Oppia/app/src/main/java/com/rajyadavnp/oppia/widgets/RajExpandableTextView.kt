package com.rajyadavnp.oppia.widgets

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import com.rajyadavnp.oppia.Oppia

class RajExpandableTextView : TextView {
    constructor(context: Context) : super(context) {
        setFont(context)
        makeTextViewResizable(this, 2, "▼ Read More", true)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setFont(context)
        makeTextViewResizable(this, 2, "▼ Read More", true)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setFont(context)
        makeTextViewResizable(this, 2, "▼ Read More", true)
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


    fun makeTextViewResizable(tv: TextView, maxLine: Int, expandText: String, viewMore: Boolean) {
        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {

                val obs = tv.viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                if (maxLine == 0) {
                    val lineEndIndex = tv.layout.getLineEnd(0)
                    val text = (tv.text.subSequence(0,
                            lineEndIndex - expandText.length - 5).toString()
                            + "... " + expandText)
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                            addClickablePartTextViewResizable(tv.text
                                    .toString(), tv, expandText,
                                    viewMore), BufferType.SPANNABLE)
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    val text = (tv.text.subSequence(0,
                            lineEndIndex - expandText.length - 5).toString()
                            + "... " + expandText)
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                            addClickablePartTextViewResizable(tv.text
                                    .toString(), tv, expandText,
                                    viewMore), BufferType.SPANNABLE)
                } else {
                    val lineEndIndex = tv.layout.getLineEnd(
                            tv.layout.lineCount - 1)
                    val text = (tv.text.subSequence(0, lineEndIndex).toString()
                            + " " + expandText)
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                            addClickablePartTextViewResizable(tv.text
                                    .toString(), tv, expandText,
                                    viewMore), BufferType.SPANNABLE)
                }
            }
        })

    }

    private fun addClickablePartTextViewResizable(strSpanned: String, tv: TextView, spanableText: String, viewMore: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(strSpanned)

        if (strSpanned.contains(spanableText)) {
            ssb.setSpan(
                    object : ClickableSpan() {

                        override fun onClick(widget: View) {

                            if (viewMore) {
                                tv.layoutParams = tv.layoutParams
                                tv.setText(tv.tag.toString(),
                                        BufferType.SPANNABLE)
                                tv.invalidate()
                                makeTextViewResizable(tv, -5, "▲ Read Less",
                                        false)
                            } else {
                                tv.layoutParams = tv.layoutParams
                                tv.setText(tv.tag.toString(),
                                        BufferType.SPANNABLE)
                                tv.invalidate()
                                makeTextViewResizable(tv, 2, "▼ Read More",
                                        true)
                            }

                        }
                    }, strSpanned.indexOf(spanableText),
                    strSpanned.indexOf(spanableText) + spanableText.length, 0)

        }
        return ssb
    }
}