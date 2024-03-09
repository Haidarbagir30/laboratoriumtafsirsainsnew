package com.learning.tafsirsainsapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.tafsirsainsapp.CategoryActivity
import com.learning.tafsirsainsapp.databinding.ItemBodBinding
import com.learning.tafsirsainsapp.databinding.ItemHomeBinding
import com.learning.tafsirsainsapp.indo.DetailsActivity
import com.learning.tafsirsainsapp.models.BooksModel
import com.learning.tafsirsainsapp.models.HomeModel
import com.learning.tafsirsainsapp.utils.SpringScrollHelper
import com.learning.tafsirsainsapp.utils.loadOnline


const val LAYOUT_HOME = 0
const val LAYOUT_BOD = 1

class HomeAdapter(val list: ArrayList<HomeModel>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class HomeItemViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val mViewPool = RecyclerView.RecycledViewPool()
        fun bind(model: HomeModel, context: Context) {
            binding.apply {
                model.apply {
                    mCategoryTitle.text = catTitle

                    mSeeAllBtn.setOnClickListener {
                        // handle here
                        val intent = Intent()
                        intent.putExtra("book_list",booksList)
                        intent.setClass(context,CategoryActivity::class.java)
                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            context as Activity,
                            mChildRvBooks,
                            mChildRvBooks.transitionName
                        )
                        context.startActivity(intent,options.toBundle())

                    }
                    if (booksList != null) {
                        mChildRvBooks.setupChildRv(booksList, context)
                    }
                }
            }


        }

//data yang ditampilkan dan menampilkan batasan data yang akan ditampilkan
        private fun RecyclerView.setupChildRv(list: ArrayList<BooksModel>, context: Context) {
            val maxItemsToShow = 5
            val limitedList = if (list.size > maxItemsToShow) ArrayList(list.subList(0, maxItemsToShow)) else list
            val adapter = HomeChildAdapter(limitedList, context)
            this.adapter = adapter
            setRecycledViewPool(mViewPool)
            SpringScrollHelper().attachToRecyclerView(this)
        }
    }

    class BODItemViewHolder(val binding: ItemBodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HomeModel, context: Context) {

            binding.apply {
                model.bod?.apply {
                    imageView.loadOnline(image)
                    mReadBookBtn.setOnClickListener {
                        //
                        Intent().apply {
                            putExtra("book_model",model.bod)
                            setClass(context, DetailsActivity::class.java)
                            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                context as Activity,
                                cardView,
                                cardView.transitionName
                            )
                            context.startActivity(this,options.toBundle())
                        }
                    }
                }
            }


        }
    }

    override fun getItemViewType(position: Int): Int {
        val model = list[position]
        return when (model.LAYOUT_TYPE) {
            LAYOUT_HOME -> LAYOUT_HOME
            else -> LAYOUT_BOD
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LAYOUT_HOME -> {
                HomeItemViewHolder(
                    ItemHomeBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                BODItemViewHolder(
                    ItemBodBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        when (model.LAYOUT_TYPE) {
            LAYOUT_HOME -> {
                (holder as HomeItemViewHolder).bind(model, context)
            }

            else -> {
                (holder as BODItemViewHolder).bind(model, context)
            }
        }
    }
}