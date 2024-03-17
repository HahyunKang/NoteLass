package com.app.note_lass.module.dashboard.ui

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Composable
//fun pdfviewer(
//    modifier: Modifier,
//    uri: Uri,
//    verticalarrangement: Arrangement.Vertical
//) {
//    val rendererscope = rememberCoroutineScope()
//    val mutex = remember { Mutex() }
//    val renderer by produceState<PdfRenderer?>(null, uri) {
//        rendererscope.launch(Dispatchers.IO) {
//            //uri를 파일 경로로 변환
//            val input =  ParcelFileDescriptor.open(uri.toFile(),  ParcelFileDescriptor.MODE_READ_ONLY)
//            value =  PdfRenderer(input)
//        }
//        awaitDispose {
//            //컴포저블이 해제(dispose)될 때 실행되는 작업
//            val currentrenderer = value
//            rendererscope.launch(Dispatchers.IO) {
//                mutex.withLock {
//                    currentrenderer?.close()
//                }
//            }
//        }
//    }
//    val context = LocalContext.current
//    val imageloader = LocalContext.current.cacheDir
//    val imageloadingscope = rememberCoroutineScope()
//  BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
//       //다양한 화면 밀도에서 일관된 크기를 나타내기 위해 사용
//        val width = with(LocalDensity.current) {maxWidth.toPx() }.toInt()
//        val height = (width * sqrt(2f)).toInt()
//        val pagecount by remember(renderer) { derivedStateOf{ renderer?.pageCount ?: 0 } }
//        LazyColumn(
//            verticalArrangement = verticalarrangement
//        ) {
//            items(
//                count = pagecount,
//                key = { index -> "$uri-$index" }
//            ) { index ->
//                var bitmap by remember { mutableStateOf(imageloader..?.get(cacheKey) as? Bitmap?) }
//                if (bitmap == null) {
//                    DisposableEffect(uri, index) {
//                        val job = imageloadingscope.launch(Dispatchers.IO) {
//                            val destinationbitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                            mutex.withLock {
//                                Log.e("pdf","loading pdf $uri - page $index/$pagecount")
//                                if (!coroutineContext.isActive) return@withLock
//                                try {
//                                    renderer?.let {
//                                        it.openPage(index).use { page ->
//                                            page.render(
//                                                destinationbitmap,
//                                                null,
//                                        }
//                                    }
//                                    Box(modifier = modifier.background(Color.White).aspectRatio(1f / sqrt(2f)).fillMaxWidth())
//                                } else {
//                                val request = ImageReader.Builder(width, height)
//                                    .memory(cachekey)
//                                    .data(bitmap)
//                                    .build()
//
//                                image(
//                                    modifier = modifier.background(color.white).aspectratio(1f / sqrt(2f)).fillmaxwidth(),
//                                    contentscale = contentscale.fit,
//                                    painter = rememberimagepainter(request),
//                                    contentdescription = "page ${index + 1} of $pagecount"
//                                )
//                            }
//                                null,
//                                               PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
//                                            )
//                                        }
//                                    }
//                                } catch (e: Exception) {
//                                    //just catch and return in case the renderer is being closed
//                                    return@launch
//                                }
//                            }
//                            bitmap = destinationbitmap
//                        }
//                        onDispose {
//                            job.cancel()
//         }
//        }
//    }
//}
//
//@Composable
//fun PdfViewer(){
//
//    var pdfUri by remember { mutableStateOf<Uri?>(null) }
//    var isSpen by remember { mutableStateOf(false) }
//    var mScaleFactor by remember { mutableStateOf(2f) }
//    var offsetX by remember { mutableStateOf(0f) }
//    var offsetY by remember { mutableStateOf(0f) }
//  //  val mScaleDetector = remember { ScaleGestureDetector() }
//
//
//    val pdfState = rememberHorizontalPdfReaderState(
//        resource = ResourceType.Remote("https://myreport.altervista.org/Lorem_Ipsum.pdf"),
//        isZoomEnable = true
//    )
//    HorizontalPDFReader(
//        state = pdfState,
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTransformGestures { _, pan, zoom, _ ->
//                    // Handle zoom
//                    mScaleFactor *= zoom
//                    mScaleFactor = mScaleFactor.coerceIn(1f, 5f)
//
//                    // Handle translation
//                    offsetX += pan.x
//                    offsetY += pan.y
//                }
//            }
//            .background(color = Color.Gray)
//    )
//
//}
//
//
//import androidx.compose.foundation.gestures.detectTransformGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.*
//
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.core.content.ContextCompat
//import androidx.core.net.toUri
//import androidx.lifecycle.viewmodel.compose.viewModel
//i
//
//
//@Composable
//fun PDFViewerApp() {
//    val viewModel = viewModel<PDFViewerViewModel>()
//
//    val pdfFile = "path/to/your/pdf/file.pdf".toUri() // Replace with the path to your PDF file
//
//    LaunchedEffect(pdfFile) {
//        viewModel.loadPdfFile(pdfFile)
//    }
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        PDFViewerContent(viewModel)
//    }
//}
//
//@Composable
//fun PDFViewerContent(viewModel: PDFViewerViewModel) {
//    val scaleState = remember { mutableStateOf(1f) }
//    val offsetXState = remember { mutableStateOf(0f) }
//    val offsetYState = remember { mutableStateOf(0f) }
//
//    val pdfRenderer = viewModel.pdfRenderer
//    val currentPage = viewModel.currentPage
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//            .pointerInput(Unit) {
//                detectTransformGestures { _, pan, zoom, _ ->
//                    scaleState.value *= zoom
//                    offsetXState.value += pan.x
//                    offsetYState.value += pan.y
//                }
//            }
//    ) {
//        if (pdfRenderer != null && currentPage != null) {
//            val bitmap = remember { mutableStateOf<Bitmap?>(null) }
//
//            DisposableEffect(currentPage) {
//                val bitmapResult = viewModel.loadPageBitmap(currentPage)
//                bitmap.value = bitmapResult
//                onDispose {
//                    bitmapResult?.recycle()
//                }
//            }
//
//            bitmap.value?.let { pdfBitmap ->
//                Image(
//                    bitmap = pdfBitmap.asImageBitmap(),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .graphicsLayer(
//                            scaleX = scaleState.value,
//                            scaleY = scaleState.value,
//                            translationX = offsetXState.value,
//                            translationY = offsetYState.value
//                        )
//                )
//            }
//        } else {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center,
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//    }
//}
//
//class PDFViewerViewModel: ViewModel() {
//    var pdfRenderer by remember { mutableStateOf<PdfRenderer?>(null) }
//    var currentPage by remember { mutableStateOf<PdfRenderer.Page?>(null) }
//
//    fun loadPdfFile(pdfUri: Uri) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val context = LocalContext.current
//            val contentResolver = context.contentResolver
//            val pdfFileDescriptor = contentResolver.openFileDescriptor(pdfUri, "r")
//
//            pdfRenderer = PdfRenderer(pdfFileDescriptor!!)
//            currentPage = pdfRenderer?.openPage(0)
//        }
//    }
//
//    suspend fun loadPageBitmap(page: PdfRenderer.Page): Bitmap? {
//        return viewModelScope.async(Dispatchers.IO) {
//            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
//            page.render(bitmap, null, null, RENDER_MODE_FOR_DISPLAY)
//            return@async bitmap
//        }.await()
//    }
//
//    onDispose {
//        currentPage?.close()
//        pdfRenderer?.close()
//    }
//}