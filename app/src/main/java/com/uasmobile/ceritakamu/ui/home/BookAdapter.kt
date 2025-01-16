package com.uasmobile.ceritakamu.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.databinding.ItemBookBinding
import com.uasmobile.ceritakamu.model.BookItem

class BookAdapter(
    private val onBookClick: (BookItem) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val books = mutableListOf<BookItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newBooks: List<BookItem>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position], onBookClick)
    }

    override fun getItemCount(): Int = books.size

    class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookItem, onBookClick: (BookItem) -> Unit) {
            binding.tvBookTitle.text = book.volumeInfo.title
            binding.tvBookAuthor.text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author"
            val thumbnailUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
            Log.d("BooksAdapter", "Loading thumbnail from URL: $thumbnailUrl")

            Glide.with(binding.root.context)
                .load(thumbnailUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(binding.imgBookCover)
            binding.root.setOnClickListener { onBookClick(book) }
        }
    }
}
