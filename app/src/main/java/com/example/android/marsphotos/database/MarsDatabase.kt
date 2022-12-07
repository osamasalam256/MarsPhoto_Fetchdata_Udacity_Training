package com.example.android.marsphotos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.marsphotos.domain.DomainMars

@Entity
data class DatabaseMars(@PrimaryKey
                        val id: String,
                        @ColumnInfo
                        val imgSrcUrl: String,
                        @ColumnInfo
                        val type: String,
                        @ColumnInfo
                        val price: Double)

fun List<DatabaseMars>.asDomainModel():List<DomainMars>{
    return map {
        DomainMars(
        id = it.id,
        imgSrcUrl = it.imgSrcUrl,
        price = it.price,
        type = it.type
        )
    }
}