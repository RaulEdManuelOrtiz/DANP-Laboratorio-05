package com.danp.danp_laboratorio05.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_table")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "plate")
    val carPlate: String,

    @ColumnInfo(name = "color")
    val carColor: String,

    @ColumnInfo(name = "brand")
    val carBrand: String,

    @ColumnInfo(name = "model")
    val carModel: String,
)
