package com.uasmobile.ceritakamu.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uasmobile.ceritakamu.databinding.FragmentSearchBinding
import com.uasmobile.ceritakamu.ui.home.BookAdapter
import com.uasmobile.ceritakamu.ui.home.BookDetailActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            binding.searchInput.setText(query)
        }

        viewModel.selectedLanguage.observe(viewLifecycleOwner) { language ->
            binding.searchInput.hint = "Bahasa: $language"
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            binding.textMessage.text = message
        }

        viewModel.bookResults.observe(viewLifecycleOwner) { response ->
           response.items?.forEach { book ->
                Toast.makeText(
                    requireContext(),
                    "Judul: ${book.volumeInfo.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val bookAdapter = BookAdapter { book ->
            val intent = Intent(requireContext(), BookDetailActivity::class.java).apply {
                putExtra("BOOK_DATA", book)
            }
            startActivity(intent)
        }

        binding.rvBookList.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.bookResults.observe(viewLifecycleOwner) { response ->
            if (response.items.isNullOrEmpty()) {
                binding.rvBookList.visibility = View.GONE
            } else {
                binding.rvBookList.visibility = View.VISIBLE
                bookAdapter.submitList(response.items)
            }
        }

        binding.btnIndonesia.setOnClickListener {
            viewModel.updateSelectedLanguage("id")
            viewModel.updateMessage("Bahasa Indonesia dipilih")
        }

        binding.btnEnglish.setOnClickListener {
            viewModel.updateSelectedLanguage("en")
            viewModel.updateMessage("English selected")
        }

        binding.searchIcon.setOnClickListener {
            val query = binding.searchInput.text.toString()
            viewModel.updateSearchQuery(query)
            viewModel.searchBooks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
