package com.uasmobile.ceritakamu.favoritedb

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey val id: String,
    val title: String,
    val authors: String?,
    val thumbnailUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(authors)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoriteBook> {
        override fun createFromParcel(parcel: Parcel): FavoriteBook {
            return FavoriteBook(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteBook?> {
            return arrayOfNulls(size)
        }
    }
}
