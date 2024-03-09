package com.learning.tafsirsainsapp.indo

import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.learning.tafsirsainsapp.R
import com.learning.tafsirsainsapp.data.PdfActivity
import com.learning.tafsirsainsapp.databinding.ActivityDetailBinding
import com.learning.tafsirsainsapp.databinding.LayoutProgressBinding
import com.learning.tafsirsainsapp.models.BooksModel
import com.learning.tafsirsainsapp.repository.BookRepo
import com.learning.tafsirsainsapp.utils.MyResponses
import com.learning.tafsirsainsapp.utils.loadOnline
import com.learning.tafsirsainsapp.viewmodels.BookViewModel
import com.learning.tafsirsainsapp.viewmodels.BookViewModelFactory

class DetailsActivity : AppCompatActivity() {
    val activity = this
    lateinit var binding: ActivityDetailBinding

    private val repo = BookRepo(activity)
    private val viewModel by lazy {
        ViewModelProvider(
            activity,
            BookViewModelFactory(repo)
        )[BookViewModel::class.java]
    }

    private val TAG = "Details_Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bookModel = intent.getSerializableExtra("book_model") as BooksModel

        val btnShow: ImageView = findViewById(R.id.language)

        btnShow.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.item_bottom_sheet, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)

            val flagArab: ImageView = view.findViewById(R.id.flagArab)
            val arabButton: MaterialButton = view.findViewById(R.id.mArabbtn)
            bookModel.apply {
                if (titlearab == "" || descriptionarab == "" || bookPDFarab == "" || authorarab == "" || bkdescriptarab == "" || imagearab == "") {
                    flagArab.visibility = View.GONE
                    arabButton.visibility = View.GONE
                }
            }
            arabButton.setOnClickListener {

                binding.apply {
                    bookModel.apply {
                        if (titlearab == "" || descriptionarab == "" || bookPDFarab == "" || authorarab == "" || bkdescriptarab == "" || imagearab == "") {
                            Toast.makeText(
                                this@DetailsActivity,
                                "Buku belum tersedia dalam Bahasa Arab",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            mBookTitle.text = titlearab
                            mAuthorName.text = authorarab
                            mBookDesc.text = descriptionarab
                            textView7.text = bkdescriptarab
                            mBookImage.loadOnline(imagearab)

                            mReadBookBtn.setOnClickListener {
                                setBookPdf(bookPDFarab, "${titlearab}.pdf")
                            }
                        }
                    }
                }
                dialog.dismiss()
            }

            val flagInggris: ImageView = view.findViewById(R.id.flagInggris)
            val inggrisButton: MaterialButton = view.findViewById(R.id.mInggrisbtn)
            bookModel.apply {
                if (titleinggris == "" || descriptioninggris == "" || bookPDFinggris == "" || authoringgris == "" || bkdescriptinggris == "" || imageinggris == "") {
                    flagInggris.visibility = View.GONE
                    inggrisButton.visibility = View.GONE
                }
            }
            inggrisButton.setOnClickListener {
                binding.apply {
                    bookModel.apply {
                        if (titleinggris == "" || descriptioninggris == "" || bookPDFinggris == "" || authoringgris == "" || bkdescriptinggris == "" || imageinggris == "") {
                            Toast.makeText(
                                this@DetailsActivity,
                                "Buku belum tersedia dalam Bahasa Inggris",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            mBookTitle.text = titleinggris
                            mAuthorName.text = authoringgris
                            mBookDesc.text = descriptioninggris
                            textView7.text = bkdescriptinggris
                            mBookImage.loadOnline(imageinggris)

                            mReadBookBtn.setOnClickListener {
                                setBookPdf(bookPDFinggris, "${titleinggris}.pdf")
                            }
                        }
                    }
                }
                dialog.dismiss()
            }

            val flagIndonesia: ImageView = view.findViewById(R.id.flagIndonesia)
            val indonesiaButton: MaterialButton = view.findViewById(R.id.mIndonesiabtn)
            bookModel.apply {
                if (title == "" || description == "" || bookPDF == "" || author == "" || bkdescript == "" || image == "") {
                    flagIndonesia.visibility = View.GONE
                    indonesiaButton.visibility = View.GONE
                }
            }

            indonesiaButton.setOnClickListener {
                binding.apply {
                    bookModel.apply {
                        if (title == "" || description == "" || bookPDF == "" || author == "" || bkdescript == "" || image == "") {
                            Toast.makeText(
                                this@DetailsActivity,
                                "Buku belum tersedia dalam Bahasa Indonesia",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            mBookTitle.text = title
                            mAuthorName.text = author
                            mBookDesc.text = description
                            textView7.text = bkdescript
                            mBookImage.loadOnline(image)

                            mReadBookBtn.setOnClickListener {
                                setBookPdf(bookPDF, "${title}.pdf")
                            }
                        }
                    }
                }
                dialog.dismiss()
            }

            dialog.show()
        }

        binding.apply {
            bookModel.apply {
                mBookTitle.text = title
                mAuthorName.text = author
                mBookDesc.text = description
                textView7.text = bkdescript
                mBookImage.loadOnline(image)
            }

            mReadBookBtn.setOnClickListener {
                setBookPdf(bookModel.bookPDF, "${bookModel.title}.pdf")
            }

            val dialogBinding = LayoutProgressBinding.inflate(layoutInflater)
            val dialog = Dialog(activity).apply {
                setCancelable(false)
                setContentView(dialogBinding.root)
                this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                this.window!!.setLayout(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
            }

            viewModel.downloadLiveData.observe(activity) {
                when (it) {
                    is MyResponses.Error -> {
                        dialog.dismiss()
                        Log.e(TAG, "onCreate: ${it.errorMessage}")
                    }

                    is MyResponses.Loading -> {
                        dialogBinding.mProgress.text = "${it.progress}%"
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            dialogBinding.mProgressBar.setProgress(it.progress, true)
                        } else {
                            dialogBinding.mProgressBar.progress = it.progress

                        }
                        dialog.show()
                        Log.i(TAG, "onCreate: Progress ${it.progress}")

                    }

                    is MyResponses.Success -> {
                        dialog.dismiss()
                        Log.i(TAG, "onCreate: Downloaded ${it.data}")
                        Intent().apply {
                            putExtra("book_pdf", it.data?.filePath)
                            setClass(activity, PdfActivity::class.java)
                            startActivity(this)
                        }
                    }
                }
            }

        }

    }

    private fun setBookPdf(bookPDF: String, s: String) {
        viewModel.downloadFile(bookPDF, s)
    }

    fun onBackButtonClicked(view: View) {
        onBackPressed()
    }

    fun onLanguageButtonClicked(view: View) {

    }
}