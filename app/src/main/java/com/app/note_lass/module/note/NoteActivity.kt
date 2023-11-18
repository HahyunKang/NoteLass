package com.app.note_lass.module.note

import RetrievePDFfromUrl
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.app.note_lass.databinding.ActivityNoteBinding
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy


class NoteActivity: AppCompatActivity() {


    private lateinit var _binding: ActivityNoteBinding

    private val binding get() = _binding

    private var pdfUri: Uri? = null
    private var photoUri: Uri? = null
    private var pdfString: String? = null

    private lateinit var pdfView: PDFView
    private lateinit var photoView: ImageView
    private lateinit var titleView: View
    private lateinit var titleText: TextView
    private lateinit var nextButton: TextView


    private fun getPdfUri() {
        pdfUri = intent.getParcelableExtra("pdfUri")
        photoUri = intent.getParcelableExtra("photoUri")
        pdfString = intent.getStringExtra("pdfString")
    }

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNoteBinding.inflate(layoutInflater)

        setContentView(binding.root)
        var bitmap: Bitmap? = null

        nextButton = binding.nextButton
        titleView = binding.backgroundTitle
        titleText = binding.pdfTitle
        pdfView = binding.pdf
        photoView = binding.photo

        getPdfUri()

        photoView.visibility = View.GONE
        if (pdfString != null) {
            Log.e("pdfString", pdfString!!)
            RetrievePDFfromUrl(doAfter = {
                pdfView.fromStream(it)
                    .swipeHorizontal(false)
                    .pageSnap(true)
                    .autoSpacing(true)
                    .pageFling(true)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .load()
            }).execute(pdfString!!)
        } else if (pdfUri != null) {
            pdfView.fromUri(pdfUri)
                .swipeHorizontal(false)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .load()
        } else {

            photoUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images
                        .Media.getBitmap(contentResolver, it)

                } else {
                    val source = ImageDecoder
                        .createSource(contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(source)
                }
            }
            photoView.setImageBitmap(bitmap)

        }

    }

}




