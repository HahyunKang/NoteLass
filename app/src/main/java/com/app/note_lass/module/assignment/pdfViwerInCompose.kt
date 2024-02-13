package com.app.note_lass.module.assignment

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.pratikk.jetpdfvue.state.VerticalVueReaderState
import com.pratikk.jetpdfvue.state.VueFileType
import com.pratikk.jetpdfvue.state.VueLoadState
import com.pratikk.jetpdfvue.state.VueResourceType
import com.pratikk.jetpdfvue.state.rememberVerticalVueReaderState
import com.pratikk.jetpdfvue.util.compressImageToThreshold
import kotlinx.coroutines.launch

@Composable
fun pdfViewerInCompose(
    uri: String
){

    val localImage = rememberVerticalVueReaderState(
        resource = VueResourceType.Remote(
            url   = uri ,
            fileType = VueFileType.PDF
        )
    )
            Row(
                modifier = Modifier
                    .padding
                        (
                        horizontal = 40.dp,
                        vertical = 40.dp
                    )
            ) {
                Box(
                    Modifier
                        .weight(2f)
                        .shadow(
                            elevation = 7.dp,
                            shape = RoundedCornerShape(size = 8.dp),
                            ambientColor = Color(0x0A26282B),
                            spotColor = Color(0x3D26282B)
                        )
                        .background(
                            color = Color(0xFFFFFFFF)
                        )
                        .fillMaxHeight()
                        .padding(horizontal = 24.dp)
                ) {
                    VerticalPdfViewer(verticalVueReaderState = localImage)
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    Modifier
                        .weight(1f)
                        .shadow(
                            elevation = 7.dp,
                            shape = RoundedCornerShape(size = 8.dp),
                            ambientColor = Color(0x0A26282B),
                            spotColor = Color(0x3D26282B)
                        )
                        .background(
                            color = Color(0xFFFFFFFF)
                        )
                        .fillMaxHeight()
                        .padding(horizontal = 24.dp, vertical = 15.dp)
                ) {
                   AssignmentDetail()

                }

            }
        }




@Composable
fun VerticalPdfViewer(verticalVueReaderState: VerticalVueReaderState) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = verticalVueReaderState.getImportLauncher(interceptResult = {
        it.compressImageToThreshold(2)
    })

    BoxWithConstraints(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        val configuration = LocalConfiguration.current
        val containerSize = remember {
            IntSize(constraints.maxWidth, constraints.maxHeight)
        }

        LaunchedEffect(Unit) {
            verticalVueReaderState.load(
                context = context,
                coroutineScope = scope,
                containerSize = containerSize,
                isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT,
                customResource = null
            )
        }
        when (verticalVueReaderState.vueLoadState) {
            is VueLoadState.NoDocument -> {
                Button(onClick = {
                    verticalVueReaderState.launchImportIntent(
                        context = context,
                        launcher = launcher
                    )
                }) {
                    Text(text = "Import Document")
                }
            }

            is VueLoadState.DocumentError -> {
                Column {
                    Text(text = "Error:  ${verticalVueReaderState.vueLoadState.getErrorMessage}")
                    Button(onClick = {
                        scope.launch {
                            verticalVueReaderState.load(
                                context = context,
                                coroutineScope = scope,
                                containerSize = containerSize,
                                isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT,
                                customResource = null
                            )
                        }
                    }) {
                        Text(text = "Retry")
                    }
                }
            }

            is VueLoadState.DocumentImporting -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text(text = "Importing...")
                }
            }

            is VueLoadState.DocumentLoaded -> {
                VerticalPdf(
                    modifier = Modifier.fillMaxHeight(),
                    verticalVueReaderState = verticalVueReaderState
                ) {
                    verticalVueReaderState.launchImportIntent(
                        context = context,
                        launcher = launcher
                    )
                }

            }

            is VueLoadState.DocumentLoading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text(text = "Loading ${verticalVueReaderState.loadPercent}")
                }
            }
        }
    }
}