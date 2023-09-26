package com.app.note_lass.module.note

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.viewpager2.widget.ViewPager2
import com.app.note_lass.databinding.ActivityNoteBinding
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.URI

class NoteActivity: AppCompatActivity() {


    private lateinit var _binding: ActivityNoteBinding

    private val binding get() = _binding

    private var pdfUri: Uri? = null
    private var photoUri: Uri? = null
    private lateinit var pdfView: PDFView
    private lateinit var photoView : ImageView
    private lateinit var titleView: View
    private lateinit var titleText: TextView
    private lateinit var nextButton: TextView


    private fun getPdfUri() {
         pdfUri= intent.getParcelableExtra("pdfUri")
         photoUri = intent.getParcelableExtra("photoUri")
    }


    private var is_Spen : Boolean = false



    private fun getSeekableFileDescriptor(): ParcelFileDescriptor? {
        pdfUri?.let {
            return contentResolver.openFileDescriptor(it, "r")
        }
        return null
    }


    companion object {
        const val PICK_PDF_FILE = 1
    }

    private var mScaleFactor = 2f

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNoteBinding.inflate(layoutInflater)

        setContentView(binding.root)
  //      mScaleDetector = ScaleGestureDetector(this, scaleListener)

        var bitmap: Bitmap ?= null
        nextButton = binding.nextButton
        titleView = binding.backgroundTitle
        titleText = binding.pdfTitle
        pdfView = binding.pdf
        photoView = binding.photo

        getPdfUri()
//
        val titleText =  "Photo"
//        titleText.text = pdfUri?.lastPathSegment!!
//        pdfUri?.lastPathSegment!!


       if(pdfUri!=null) {

          photoView.visibility = View.GONE

           pdfView.fromUri(pdfUri)
               .swipeHorizontal(false)
               .pageSnap(true)
               .autoSpacing(true)
               .pageFling(true)
               .pageFitPolicy(FitPolicy.BOTH)
               .load()
       }
        else{

            photoUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap= MediaStore.Images
                        .Media.getBitmap(contentResolver,it)

                } else {
                    val source = ImageDecoder
                        .createSource(contentResolver,it)
                    bitmap= ImageDecoder.decodeBitmap(source)
                }
            }
           photoView.setImageBitmap(bitmap)



       }

       }

       // renderPDF(pdfUri!!)
     //   Log.e("pdf", pdfUri!!.encodedPath.toString())

        //view pager adapter
//        val adapter = ViewPagerAdapter(this, pdf_bitmap, annoatedBitmap, pdfUri!!)
//        pdfView.adapter = adapter
//        pdfView.orientation= ViewPager2.ORIENTATION_HORIZONTAL
//
//
//        //view pager touch event
//        pdfView.getChildAt(0).setOnTouchListener { view: View, motionEvent: MotionEvent ->
//            //확대 기능
//            mScaleDetector.onTouchEvent(motionEvent)
//            Log.e("test", "ontouch")
//            //손으로 터치 시 스크롤 가능하도록
//            return@setOnTouchListener false
//
//        }


    }


//
//    //pdf 렌더링하는 함수
//    private fun renderPDF(pdfUri: Uri) {
//        try {
//
//            val renderer = PdfRenderer(getSeekableFileDescriptor()!!)
//            val pageCount = renderer.pageCount
//
//
//            for (pageIndex: Int in 0 until pageCount) {
//                val page = renderer.openPage(pageIndex)
//                pdf_bitmap.add(
//                    Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
//                )
//                annoatedBitmap.add(
//                    Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
//                )
//                page.render(
//                    pdf_bitmap[pageIndex],
//                    null,
//                    null,
//                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
//                )
//                adjustDrawingViewSizeToPdfPage(page)
//                page.close()
//
//            }
//
//            // Here, we render the page onto the Bitmap.
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun saveAnnotatedPdf() {
//
//        try {
//            val renderer = PdfRenderer(getSeekableFileDescriptor()!!)
//            val pageCount = renderer.pageCount
//
//            final_doucument = PdfDocument()
//
//
//
//            for (page_Index in 0 until pageCount) {
//                val page = renderer.openPage(page_Index)
//
//                val bitmap =
//                    Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
//                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_PRINT)
//
//
//                val combinedBitmap =
//                    annoatedBitmap[page_Index]?.let {
//                        overlayBitmap(
//                            pdf_bitmap[page_Index],
//                            it
//                        )
//                    }
//
//                val pageInfo =
//                    PdfDocument.PageInfo.Builder(page.width, page.height, page_Index + 1)
//                        .create()
//                val pdfPage = final_doucument?.startPage(pageInfo)
//
//                val canvas = pdfPage?.canvas
//                if (combinedBitmap != null) {
//                    canvas?.drawBitmap(combinedBitmap, 0F, 0F, null)
//                }
//
//                final_doucument?.finishPage(pdfPage)
//
//                Log.e("examine_log", "examine1")
//
//                // 이후의 코드는 그대로 유지
//
//                page.close()
//
//            }
//
//            renderer.close()
//
//
//            save()
//
//
//            //  val outputStream = FileOutputStream(getOu)
//            Toast.makeText(this, "Annotated PDF saved successfully!", Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this, "Failed to save annotated PDF!", Toast.LENGTH_SHORT).show()
//        }
//
//
//    }
//
//    //viewpager를 pdf파일 크기에 맞추면서 필기 범위 제한
//    private fun adjustDrawingViewSizeToPdfPage(page: PdfRenderer.Page) {
//
//
//        pdfView.layoutParams.width = page.width.toInt()
//        pdfView.layoutParams.height = page.height.toInt()
//
//        pdfView.requestLayout()
//
//        val screenWidth = getScreenWidthInPixels(this)
//        //    val screenHeight=  getScreenHeightInPixels(this)
//
//        pdfView.x = ((screenWidth - page.width) / 2).toFloat()
//    }
//
//    private fun getScreenWidthInPixels(context: Context): Int {
//        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        return displayMetrics.widthPixels
//    }
//
//    private fun getScreenHeightInPixels(context: Context): Int {
//        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        return displayMetrics.heightPixels
//    }
//
//
//    //저장할 때 pdf파일 위에 필기 파일 올려 저장
//    private fun overlayBitmap(background: Bitmap, foreground: Bitmap): Bitmap {
//        val resultBitmap =
//            Bitmap.createBitmap(background.width, background.height, background.config)
//        val canvas = Canvas(resultBitmap)
//        canvas.drawBitmap(background, 0F, 0F, null)
//        canvas.drawBitmap(foreground, 0F, 0F, null)
//        return resultBitmap
//    }
//
//    private val WRITE_REQUEST_CODE = 43
//
//    fun save() {
//        try {
//            /**
//             * SAF 파일 편집
//             */
//
//            val fileName = "note-lass.pdf"
//            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
//            intent.addCategory(Intent.CATEGORY_OPENABLE)
//            intent.type = "application/pdf"
//            intent.putExtra(Intent.EXTRA_TITLE, fileName)
//            startActivityForResult(intent, WRITE_REQUEST_CODE)
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == WRITE_REQUEST_CODE && resultCode == RESULT_OK) {
//            if (data != null) {
//                final_url = data.data
//                addPdf(final_url)
//            }
//
//        }
//    }
//
//    fun addPdf(uri: Uri?) {
//        try {
//            pfd = this.contentResolver.openFileDescriptor(uri!!, "w")
//            fileOutputStream = FileOutputStream(pfd?.getFileDescriptor())
//
//            final_doucument?.writeTo(fileOutputStream)
//            fileOutputStream?.close()
//
//            final_doucument?.close()
//
//
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
//    }
//
//    //확대할 때 호출되는 리스너
//    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            mScaleFactor *= detector.scaleFactor
//
//            // Don't let the object get too small or too large.
//            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 5.0f))
//            Log.e("scale",mScaleFactor.toString())
//
//            pdfView.scaleX = mScaleFactor
//            pdfView.scaleY = mScaleFactor
//
//
//            return true
//        }
//
//    }
//    var lastTouchX = 0f
//    var lastTouchY = 0f
//    var offsetX = 0f
//    var offsetY = 0f
//
//    //pdf파일 범위 밖의 부분을 터치했을 때 아래 event 발생. 확대는 잘 동작, 스크롤 할 때 위치가 흔들리는 등 이슈 발생해서 주석 처리함
//    override fun onTouchEvent(ev: MotionEvent): Boolean {
//
//        titleView.bringToFront()
//        titleText.bringToFront()
//
//        if (ev.getToolType(0) == MotionEvent.TOOL_TYPE_STYLUS) {
//            // pdfView.isUserInputEnabled= false
//            return false
//        }
//        // Let the ScaleGestureDetector inspect all events.
//        mScaleDetector.onTouchEvent(ev)
//
//
//
//        when (ev.action) {
//            MotionEvent.ACTION_DOWN -> {
//                lastTouchX = ev.x
//                lastTouchY = ev.y
//                return true
//            }
//
//            MotionEvent.ACTION_MOVE -> {
//
//                val deltaX = ev.x - lastTouchX
//                val deltaY = ev.y - lastTouchY
//
//                // 뷰의 위치를 변위값에 따라 조정
//                offsetX += deltaX
//                offsetY += deltaY
//                val scaleWidth = pdfView.width * pdfView.scaleX
//                val scaledHeight = pdfView.height * pdfView.scaleY
//
//                pdfView.translationY = offsetY
//
//                // 마지막 터치 위치 갱신
//                lastTouchX = ev.x
//                lastTouchY = ev.y
//
//                return true
//            }
//
//
//            else -> return super.onTouchEvent(ev)
//        }
//
//        return true
//    }





