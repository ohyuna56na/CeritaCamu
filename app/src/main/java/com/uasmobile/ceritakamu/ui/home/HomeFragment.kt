package com.uasmobile.ceritakamu.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uasmobile.ceritakamu.databinding.FragmentHomeBinding
import com.uasmobile.ceritakamu.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: BookAdapter
    private val repository = BookRepository()
    private var startIndex = 0
    private val maxResults = 40

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookAdapter { book ->
            val intent = Intent(requireContext(), BookDetailActivity::class.java)
            intent.putExtra("BOOK_DATA", book)
            startActivity(intent)
        }
        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBooks.adapter = adapter

        fetchBooks()
    }

    private fun fetchBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val query = "novels+fiction+comics"
                val response = repository.fetchBooks(query, startIndex, maxResults)

                val books = response.items ?: emptyList()
                startIndex += maxResults

                withContext(Dispatchers.Main) {
                    adapter.submitList(books)
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching books", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to fetch books: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
