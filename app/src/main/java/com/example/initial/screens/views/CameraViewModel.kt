package com.example.initial.screens.views;

import android.app.Application;
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class CameraState{
    object Idle: CameraState()
    object Loading: CameraState()
    data class Error(val message: String) : CameraState()
    data class PhotoCaptured(val base64String: String) : CameraState()
}

class CameraViewModel(application:Application) : AndroidViewModel(application) {
    val _cameraState = mutableStateOf<CameraState>(CameraState.Idle)
    val cameraState: State<CameraState> = _cameraState

    private val _base64Photo = MutableLiveData<String?>()
    val base64Photo: LiveData<String?> = _base64Photo

    fun savePhoto(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cameraState.value = CameraState.Loading
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                val base64String: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                _cameraState.value = CameraState.PhotoCaptured(base64String)
            }
            catch (ex: Exception) {
                _cameraState.value = CameraState.Error(ex.message ?: "Unknown Error")
            }
        }
    }

    fun resetState() {
        _cameraState.value = CameraState.Idle
    }

    fun setBase64Photo(base64String: String) {
        _base64Photo.value = base64String
    }
}