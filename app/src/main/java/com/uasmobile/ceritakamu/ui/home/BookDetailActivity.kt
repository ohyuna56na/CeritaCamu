package com.uasmobile.ceritakamu.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.databinding.ActivityDetailBinding
import com.uasmobile.ceritakamu.favoritedb.FavoriteBook
import com.uasmobile.ceritakamu.model.BookItem
import com.uasmobile.ceritakamu.model.VolumeInfo
import com.uasmobile.ceritakamu.ui.dashboard.SharedViewModel
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookItem = intent.getParcelableExtra<BookItem>("BOOK_DATA")
        val favoriteBook = intent.getParcelableExtra<FavoriteBook>("EXTRA_BOOK")

        if (bookItem != null) {
            showBookDetails(bookItem.volumeInfo)
            handleFavoriteState(bookItem)
        } else if (favoriteBook != null) {
            showBookDetails(favoriteBook.toVolumeInfo())
            handleFavoriteState(favoriteBook.toBookItem())
        } else {
            Toast.makeText(this, "Data buku tidak tersedia", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteIcon()
            handleFavorite(bookItem ?: favoriteBook?.toBookItem())
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Detail Buku"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showBookDetails(volumeInfo: VolumeInfo) {
        binding.titleTextView.text = volumeInfo.title
        binding.authorsTextView.text = volumeInfo.authors?.joinToString(", ") ?: "Unknown Authors"
        binding.publisherTextView.text = volumeInfo.publisher ?: "Unknown Publisher"
        binding.publishedDateTextView.text = volumeInfo.publishedDate ?: "Unknown Date"
        binding.descriptionTextView.text = volumeInfo.description ?: "No description available"
        binding.pageCountTextView.text = volumeInfo.pageCount?.toString() ?: "Unknown Page Count"
        binding.languageTextView.text = "Language: ${volumeInfo.language ?: "Unknown"}"
        binding.categoriesTextView.text = volumeInfo.categories?.joinToString(", ") ?: "Unknown Categories"
        binding.printTypeTextView.text = "Print Type: ${volumeInfo.printType ?: "Unknown"}"
        binding.maturityRatingTextView.text = "Maturity Rating: ${volumeInfo.maturityRating ?: "Unknown"}"
        binding.allowAnonLoggingTextView.text = "Anonymous Logging: ${volumeInfo.allowAnonLogging?.toString() ?: "Unknown"}"
        binding.contentVersionTextView.text = "Content Version: ${volumeInfo.contentVersion ?: "Unknown"}"

        val readingModes = "Text: ${volumeInfo.readingModes?.text ?: "Unknown"}, Image: ${volumeInfo.readingModes?.image ?: "Unknown"}"
        binding.readingModesTextView.text = "Reading Modes: $readingModes"

        val panelizationSummary = "Epub Bubbles: ${volumeInfo.panelizationSummary?.containsEpubBubbles ?: "Unknown"}, " +
                "Image Bubbles: ${volumeInfo.panelizationSummary?.containsImageBubbles ?: "Unknown"}"
        binding.panelizationSummaryTextView.text = "Panelization Summary: $panelizationSummary"

        val thumbnailUrl = volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")

        Glide.with(this)
            .load(thumbnailUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_image)
            .into(binding.thumbnailImageView)

        binding.previewLinkButton.setOnClickListener { openLink(volumeInfo.previewLink) }
        binding.infoLinkButton.setOnClickListener { openLink(volumeInfo.infoLink) }
    }

    private fun openLink(url: String?) {
        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "Link is not available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun updateFavoriteIcon() {
        val icon = if (isFavorite) R.drawable.favorite_on else R.drawable.ic_favorite_off
        binding.favoriteButton.setImageDrawable(ContextCompat.getDrawable(this, icon))
    }

    private fun handleFavorite(book: BookItem?) {
        book?.let {
            lifecycleScope.launch {
                if (isFavorite) {
                    sharedViewModel.addFavoriteBook(it)
                    Toast.makeText(this@BookDetailActivity, "Ditambahkan ke Favorit", Toast.LENGTH_SHORT).show()
                } else {
                    sharedViewModel.removeFavoriteBook(it)
                    Toast.makeText(this@BookDetailActivity, "Dihapus dari Favorit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleFavoriteState(book: BookItem) {
        lifecycleScope.launch {
            isFavorite = sharedViewModel.isFavorite(book)
            updateFavoriteIcon()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
