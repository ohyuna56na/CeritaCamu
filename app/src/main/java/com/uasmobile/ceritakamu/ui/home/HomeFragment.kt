package com.uasmobile.ceritakamu.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.uasmobile.ceritakamu.databinding.FragmentHomeBinding
import com.uasmobile.ceritakamu.paging.BookPagingAdapter
import com.uasmobile.ceritakamu.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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

        val viewModel: HomeViewModel by viewModels { ViewModelFactory(BookRepository()) }
        val adapter = BookPagingAdapter { book ->
            val intent = Intent(requireContext(), BookDetailActivity::class.java)
            intent.putExtra("BOOK_DATA", book)
            startActivity(intent)
        }

        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBooks.adapter = adapter

        lifecycleScope.launch {
            viewModel.getBooks("novel+fiction").collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

}
