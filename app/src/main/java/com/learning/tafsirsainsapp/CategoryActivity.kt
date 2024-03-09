package com.learning.tafsirsainsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.tafsirsainsapp.adapters.CategoryAdapter
import com.learning.tafsirsainsapp.databinding.ActivityCategoryBinding
import com.learning.tafsirsainsapp.models.BooksModel
import com.learning.tafsirsainsapp.utils.SpringScrollHelper
import java.util.Locale
import androidx.appcompat.widget.SearchView

class CategoryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)

    }
    private val activity = this

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<BooksModel>
    private lateinit var dataList:ArrayList<BooksModel>


    private val list = ArrayList<BooksModel>()
    private val adapter = CategoryAdapter(list, activity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.apply {
            mRvCategory.adapter = adapter
            SpringScrollHelper().attachToRecyclerView(mRvCategory)
            val bookList = intent.getSerializableExtra("book_list") as ArrayList<BooksModel>
            bookList.forEach {
                list.add(it)
            }
            recyclerView = findViewById(R.id.mRvCategory)
            searchView = findViewById(R.id.search)

            recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)
            recyclerView.setHasFixedSize(true)

            searchList = arrayListOf<BooksModel>()
            dataList = arrayListOf<BooksModel>()
            getData()
        }

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    dataList.forEach {
                        if (it.title.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                return false
            }
        })
    }

    private fun getData() {
        for (i in adapter.list) {
            dataList.add(i)
        }
        searchList.addAll(dataList)
        recyclerView.adapter = CategoryAdapter(searchList, activity)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        with(window) {
            sharedElementReenterTransition = null
            sharedElementReturnTransition = null
        }
        binding.mRvCategory.transitionName = null

    }
    fun onBackButtonClicked(view: View) {
        onBackPressed()
    }


}