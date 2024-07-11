package com.example.imagesearch.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.imagesearch.databinding.ActivityMainBinding
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.R
import com.example.imagesearch.data.Repository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 앱 실행 시 SearchFragment 표시
        if (savedInstanceState == null) {
            showFragment(SearchFragment(), R.id.fragment_container_search)
            showSearchBar()
        }

        // 이미지 검색 클릭 시 SearchFragment 표시
        binding.btnSearch.setOnClickListener {
            showFragment(SearchFragment(), R.id.fragment_container_search)
            binding.fragmentContainerSearch.visibility = View.VISIBLE
            binding.fragmentContainerKeep.visibility = View.GONE
            showSearchBar()
        }

        // 이미지 보관 클릭 시 KeepFragment 표시
        binding.btnKeep.setOnClickListener {
            showFragment(KeepFragment(), R.id.fragment_container_keep)
            binding.fragmentContainerSearch.visibility = View.GONE
            binding.fragmentContainerKeep.visibility = View.VISIBLE
            hideSearchBar()
        }

        // 검색 실행
        binding.btnExecuteSearch.setOnClickListener {
            searchImages()
            setLastSearchToSearchField()
            hideKeyboard(it)
        }

        observeViewModel()

        val dateTime = Date()
        formatDateTime(dateTime)
    }

    fun showFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeViewModel() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    // 검색어 저장
    private fun searchImages() {
        val searchQuery = binding.etSearch.text.toString()
        viewModel.searchImages(
            query = searchQuery,
            sort = "accuracy",
            page = 1,
            size = 20
        )
        saveLastSearch(searchQuery)
        showFragment(SearchFragment(), R.id.fragment_container_search)
        showSearchBar()
    }

    private fun saveLastSearch(searchQuery: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(LAST_SEARCH_KEY, searchQuery)
        editor.apply()
    }

    // 마지막 검색어를 검색창에 표시
    private fun setLastSearchToSearchField() {
        val lastSearch = getLastSearch()
        lastSearch?.let {
            binding.etSearch.setText(it)
            binding.etSearch.setSelection(it.length)
        }
    }

    private fun getLastSearch(): String? {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(LAST_SEARCH_KEY, null)
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun formatDateTime(dateTime: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(dateTime)
    }

    // 검색바 숨기기
    private fun hideSearchBar() {
        binding.etSearch.visibility = View.GONE
        binding.btnExecuteSearch.visibility = View.GONE
    }

    // 검색바 보이기
    private fun showSearchBar() {
        binding.etSearch.visibility = View.VISIBLE
        binding.btnExecuteSearch.visibility = View.VISIBLE
    }

    companion object {
        private const val PREFS_NAME = "SearchPrefs"
        private const val LAST_SEARCH_KEY = "last_search"
    }
}