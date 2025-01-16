package com.uasmobile.ceritakamu.model

import android.os.Parcelable
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
