package com.uasmobile.ceritakamu.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.uasmobile.ceritakamu.databinding.FragmentDashboardBinding
import com.uasmobile.ceritakamu.ui.home.BookDetailActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("DashboardFragment", "onCreateView called")
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DashboardFragment", "onViewCreated called")

        favoriteAdapter = FavoriteAdapter(emptyList()) { favoriteBook ->
            val intent = Intent(activity, BookDetailActivity::class.java)
            intent.putExtra("EXTRA_BOOK", favoriteBook)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = favoriteAdapter

        binding.progressBar.visibility = View.VISIBLE

        sharedViewModel.favoriteBooks.observe(viewLifecycleOwner) { favoriteBooks ->
            binding.progressBar.visibility = View.GONE
            favoriteAdapter.updateBooks(favoriteBooks)
            binding.recyclerView.adapter = favoriteAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("DashboardFragment", "onDestroyView called")
        _binding = null
    }
}
