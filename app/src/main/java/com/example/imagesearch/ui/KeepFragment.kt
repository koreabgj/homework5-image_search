package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R

class KeepFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Replace this with your data and adapter
        val data = listOf("Item 1", "Item 2", "Item 3")
        val adapter = SearchAdapter(data)
        recyclerView.adapter = adapter

        return view
    }

    private fun GridLayoutManager(searchFragment: KeepFragment, i: Int): GridLayoutManager {
        TODO("Not yet implemented")
    }
}