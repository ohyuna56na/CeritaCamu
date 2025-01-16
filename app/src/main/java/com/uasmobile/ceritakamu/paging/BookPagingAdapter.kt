package com.uasmobile.ceritakamu.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uasmobile.ceritakamu.R
import com.uasmobile.ceritakamu.databinding.ItemBookBinding
import com.uasmobile.ceritakamu.model.BookItem

class BookPagingAdapter(
    private val onBookClick: (BookItem) -> Unit
) : PagingDataAdapter<BookItem, BookPagingAdapter.BookViewHolder>(BOOK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        if (book != null) {
            holder.bind(book, onBookClick)
        }
    }

    class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookItem, onBookClick: (BookItem) -> Unit) {
            binding.tvBookTitle.text = book.volumeInfo.title
            binding.tvBookAuthor.text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author"
            val thumbnailUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")

            Glide.with(binding.root.context)
                .load(thumbnailUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(binding.imgBookCover)

            binding.root.setOnClickListener { onBookClick(book) }
        }
    }

    companion object {
        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<BookItem>() {
            override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}