package com.example.initial.screens.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
fun CameraView(
    cameraViewModel: CameraViewModel,
    onImageCaptured: (String) -> Unit, onError: (ImageCaptureException) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    val cameraState by cameraViewModel.cameraState
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val imageCapture = ImageCapture.Builder().build()

    LaunchedEffect(key1 = cameraProviderFuture) {
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
        val preview =
            Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageCapture
            )
        } catch (ex: Exception) {
            cameraViewModel._cameraState.value = CameraState.Error(ex.message ?: "Unknown error")
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            val photoFile = createFile(context)
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: OutputFileResults) {
                        val uri = Uri.fromFile(photoFile)
                        val bitmap = uriToBitmap(uri, context)
                        cameraViewModel.savePhoto(bitmap)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        onError(exception)
                    }
                })
        }) {
            Text(text = "Capture")
        }
    }

    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

    when (cameraState) {
        is CameraState.Idle -> {}
        is CameraState.Loading -> {}
        is CameraState.Error -> {
            val error = (cameraState as CameraState.Error).message
            Text(text = error)
        }

        is CameraState.PhotoCaptured -> {
            val base64String = (cameraState as CameraState.PhotoCaptured).base64String
            onImageCaptured(base64String)
        }
    }
}

private fun createFile(context: Context): File {
    val dir = context.filesDir
    return File(dir, "temp_image.jpg")
}

private fun convertUriToBase64(uri: Uri, context: Context): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val byteArrayOutputStream = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var len: Int
    while (inputStream!!.read(buffer).also { len = it } != 1) {
        byteArrayOutputStream.write(buffer, 0, len)
    }
    val bytes = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

private fun uriToBitmap(uri: Uri, context: Context): Bitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}