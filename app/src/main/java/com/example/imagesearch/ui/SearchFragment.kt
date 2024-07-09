package com.example.imagesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: MainViewModel

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(thumbnailUrl: String, position: Int) {
                // 이미지 클릭시 보관함으로 이동
                viewModel.addThumbnailUrl(thumbnailUrl)
                navigateToKeepFragment(thumbnailUrl)
            }
        })

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        // 썸네일 이미지 리스트 설정
        val thumbnailUrls = arguments?.getStringArrayList(THUMBNAIL_URLS_KEY)
        thumbnailUrls?.let {
            viewModel.setThumbnailUrls(it)
        }

        viewModel.imageDocuments.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    fun navigateToKeepFragment(thumbnailUrl: String) {
        (requireActivity() as MainActivity).navigateToKeepFragment(thumbnailUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}