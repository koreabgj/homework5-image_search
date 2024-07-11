package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.FragmentKeepBinding

class KeepFragment : Fragment() {

    private var _binding: FragmentKeepBinding? = null
    private val binding: FragmentKeepBinding
        get() = _binding!!
    private lateinit var adapter: KeepAdapter
    private lateinit var viewModel: MainViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentKeepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = KeepAdapter(mutableListOf(), object : KeepAdapter.OnItemClickListener {
            override fun onItemClick(thumbnail: ThumbnailModel) {
                viewModel.removeThumbnail(thumbnail)
            }
        })

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewModel.thumbnails.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}