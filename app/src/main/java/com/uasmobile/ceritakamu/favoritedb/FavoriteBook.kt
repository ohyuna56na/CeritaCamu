package com.uasmobile.ceritakamu.favoritedb

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uasmobile.ceritakamu.model.BookItem
import com.uasmobile.ceritakamu.model.ImageLinks
import com.uasmobile.ceritakamu.model.PanelizationSummary
import com.uasmobile.ceritakamu.model.ReadingModes
import com.uasmobile.ceritakamu.model.VolumeInfo

@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey val id: String,
    val title: String,
    val authors: String?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val readingModesText: Boolean?,
    val readingModesImage: Boolean?,
    val pageCount: Int?,
    val printType: String?,
    val categories: String?,
    val maturityRating: String?,
    val allowAnonLogging: Boolean?,
    val contentVersion: String?,
    val containsEpubBubbles: Boolean?,
    val containsImageBubbles: Boolean?,
    val thumbnailUrl: String?,
    val language: String?,
    val previewLink: String?,
    val infoLink: String?,
    val canonicalVolumeLink: String?,
    val userId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        title = parcel.readString() ?: "",
        authors = parcel.readString(),
        publisher = parcel.readString(),
        publishedDate = parcel.readString(),
        description = parcel.readString(),
        readingModesText = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        readingModesImage = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        pageCount = parcel.readValue(Int::class.java.classLoader) as? Int,
        printType = parcel.readString(),
        categories = parcel.readString(),
        maturityRating = parcel.readString(),
        allowAnonLogging = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        contentVersion = parcel.readString(),
        containsEpubBubbles = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        containsImageBubbles = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        thumbnailUrl = parcel.readString(),
        language = parcel.readString(),
        previewLink = parcel.readString(),
        infoLink = parcel.readString(),
        canonicalVolumeLink = parcel.readString(),
        userId = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(authors)
        parcel.writeString(publisher)
        parcel.writeString(publishedDate)
        parcel.writeString(description)
        parcel.writeValue(readingModesText)
        parcel.writeValue(readingModesImage)
        parcel.writeValue(pageCount)
        parcel.writeString(printType)
        parcel.writeString(categories)
        parcel.writeString(maturityRating)
        parcel.writeValue(allowAnonLogging)
        parcel.writeString(contentVersion)
        parcel.writeValue(containsEpubBubbles)
        parcel.writeValue(containsImageBubbles)
        parcel.writeString(thumbnailUrl)
        parcel.writeString(language)
        parcel.writeString(previewLink)
        parcel.writeString(infoLink)
        parcel.writeString(canonicalVolumeLink)
        parcel.writeInt(userId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FavoriteBook> {
        override fun createFromParcel(parcel: Parcel): FavoriteBook = FavoriteBook(parcel)
        override fun newArray(size: Int): Array<FavoriteBook?> = arrayOfNulls(size)
    }

    fun toVolumeInfo(): VolumeInfo {
        return VolumeInfo(
            title = title,
            authors = authors?.split(", "),
            publisher = publisher,
            publishedDate = publishedDate,
            description = description,
            readingModes = ReadingModes(
                text = readingModesText,
                image = readingModesImage
            ),
            pageCount = pageCount,
            printType = printType,
            categories = categories?.split(", "),
            maturityRating = maturityRating,
            allowAnonLogging = allowAnonLogging,
            contentVersion = contentVersion,
            panelizationSummary = PanelizationSummary(
                containsEpubBubbles = containsEpubBubbles,
                containsImageBubbles = containsImageBubbles
            ),
            imageLinks = ImageLinks(thumbnail = thumbnailUrl),
            language = language,
            previewLink = previewLink,
            infoLink = infoLink,
            canonicalVolumeLink = canonicalVolumeLink
        )
    }

    fun toBookItem(): BookItem {
        return BookItem(
            id = id,
            volumeInfo = toVolumeInfo()
        )
    }
}
