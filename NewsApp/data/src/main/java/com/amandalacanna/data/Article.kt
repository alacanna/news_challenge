package com.amandalacanna.data

import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import java.util.*

@Parcelize
@SuppressLint("ParcelCreator")
data class Article (val source: Source,
                    val author: String?,
                    val title: String,
                    val description: String,
                    val url: String,
                    val urlToImage: String?,
                    val publishedAt: Date?): Parcelable