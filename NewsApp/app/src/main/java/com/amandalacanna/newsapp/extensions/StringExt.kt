package com.amandalacanna.newsapp.extensions

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan


fun String?.color(color: Int, context: Context, isBold: Boolean? = false): SpannableString {
    return this?.let {
        val spannableString = SpannableString(this)
        val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(context, color))
        spannableString.setSpan(foregroundSpan, 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        if (isBold != null && isBold){
            spannableString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        spannableString
    }?: SpannableString("")
}