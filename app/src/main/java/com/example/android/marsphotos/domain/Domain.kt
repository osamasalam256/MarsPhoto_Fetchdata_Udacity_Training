package com.example.android.marsphotos.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DomainMars(val id: String,
                  val imgSrcUrl: String,
                  val type: String,
                  val price: Double): Parcelable {

    val isRental
        get() = type == "rent"
}