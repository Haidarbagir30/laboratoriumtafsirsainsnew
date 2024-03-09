package com.learning.tafsirsainsapp.data

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.learning.tafsirsainsapp.databinding.ActivityPdfBinding

class PdfActivity : AppCompatActivity() {
    lateinit var binding: ActivityPdfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            val bookPDF = intent.getStringExtra("book_pdf").toString()

            // Setting PDFView properties
            pdfView.fromUri(Uri.parse(bookPDF))
                .swipeHorizontal(true)
                .enableSwipe(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .password(null)
                .scrollHandle(null) // Scroll handle can be set programmatically if needed
                .enableAntialiasing(true)
                .spacing(0) // in dp
                .fitEachPage(true)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load()
        }
    }

    fun onBackButtonClicked(view: View) {
        onBackPressed()
    }
}
