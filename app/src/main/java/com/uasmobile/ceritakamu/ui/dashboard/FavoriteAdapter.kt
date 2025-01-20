package com.uasmobile.ceritakamu.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uasmobile.ceritakamu.databinding.ItemFavoriteBookBinding
import com.uasmobile.ceritakamu.favoritedb.FavoriteBook

class FavoriteAdapter(
    private var books: List<FavoriteBook> = listOf(),
    private val onClick: (FavoriteBook) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFavoriteBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: FavoriteBook) {
            binding.titleTextView.text = book.title
            val thumbnailUrl = book.thumbnailUrl?.replace("http://", "https://")

            Glide.with(binding.root.context)
                .load(thumbnailUrl)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                onClick(book)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<FavoriteBook>) {
        books = newBooks
        notifyDataSetChanged()
    }
}