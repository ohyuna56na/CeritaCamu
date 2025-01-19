package com.uasmobile.ceritakamu.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.uasmobile.ceritakamu.databinding.FragmentHomeBinding
import com.uasmobile.ceritakamu.paging.BookPagingAdapter
import com.uasmobile.ceritakamu.repository.BookRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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

        Log.d("HomeFragment", "Showing ProgressBar")
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                viewModel.getBooks("novel+fiction").collectLatest { pagingData ->
                    Log.d("HomeFragment", "Hiding ProgressBar, data loaded")
                    binding.progressBar.visibility = View.GONE
                    adapter.submitData(pagingData)
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error loading data: ${e.message}")
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}
