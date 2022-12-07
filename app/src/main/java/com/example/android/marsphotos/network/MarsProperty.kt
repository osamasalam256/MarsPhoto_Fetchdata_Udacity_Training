package com.example.android.marsphotos.network

import com.example.android.marsphotos.database.DatabaseMars
import com.example.android.marsphotos.domain.DomainMars
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * This data class defines a Mars photo which includes an ID, and the image URL.
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
@JsonClass(generateAdapter = true)
data class MarsPropertyContainer(val marsProperties: List<NetworkMarsProperty>)


@JsonClass(generateAdapter = true)
data class NetworkMarsProperty(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double)

fun MarsPropertyContainer.asDomainModel(): List<DomainMars>{
    return marsProperties.map {
        DomainMars(
            id = it.id,
            imgSrcUrl = it.imgSrcUrl,
            price = it.price,
            type = it.type
        )

    }
}

fun MarsPropertyContainer.asDataBaseModel(): List<DatabaseMars>{
    return marsProperties.map{
        DatabaseMars(
            id = it.id,
            imgSrcUrl = it.imgSrcUrl,
            price = it.price,
            type = it.type
        )
    }
}

