package com.uasmobile.ceritakamu.model

import android.os.Parcelable
import com.uasmobile.ceritakamu.favoritedb.FavoriteBook
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookResponse(
    val items: List<BookItem>?
) : Parcelable

@Parcelize
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
) : Parcelable

@Parcelize
data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val readingModes: ReadingModes?,
    val pageCount: Int?,
    val printType: String?,
    val categories: List<String>?,
    val maturityRating: String?,
    val allowAnonLogging: Boolean?,
    val contentVersion: String?,
    val panelizationSummary: PanelizationSummary?,
    val imageLinks: ImageLinks?,
    val language: String?,
    val previewLink: String?,
    val infoLink: String?,
    val canonicalVolumeLink: String?
) : Parcelable

@Parcelize
data class ReadingModes(
    val text: Boolean?,
    val image: Boolean?
) : Parcelable

@Parcelize
data class PanelizationSummary(
    val containsEpubBubbles: Boolean?,
    val containsImageBubbles: Boolean?
) : Parcelable

@Parcelize
data class ImageLinks(
    val thumbnail: String?
) : Parcelable

fun BookItem.toFavoriteBook(userId: Int): FavoriteBook {
    return FavoriteBook(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors?.joinToString(", "),
        publisher = this.volumeInfo.publisher,
        publishedDate = this.volumeInfo.publishedDate,
        description = this.volumeInfo.description,
        readingModesText = this.volumeInfo.readingModes?.text,
        readingModesImage = this.volumeInfo.readingModes?.image,
        pageCount = this.volumeInfo.pageCount,
        printType = this.volumeInfo.printType,
        categories = this.volumeInfo.categories?.joinToString(", "),
        maturityRating = this.volumeInfo.maturityRating,
        allowAnonLogging = this.volumeInfo.allowAnonLogging,
        contentVersion = this.volumeInfo.contentVersion,
        containsEpubBubbles = this.volumeInfo.panelizationSummary?.containsEpubBubbles,
        containsImageBubbles = this.volumeInfo.panelizationSummary?.containsImageBubbles,
        thumbnailUrl = this.volumeInfo.imageLinks?.thumbnail,
        language = this.volumeInfo.language,
        previewLink = this.volumeInfo.previewLink,
        infoLink = this.volumeInfo.infoLink,
        canonicalVolumeLink = this.volumeInfo.canonicalVolumeLink,
        userId = userId
    )
}
