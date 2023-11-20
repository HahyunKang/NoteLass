package com.app.note_lass.module.upload.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.upload.data.UploadState
import com.app.note_lass.module.group.domain.repository.CreateNoticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    val createNoticeUseCase: CreateNoticeUseCase
) : ViewModel(){

    private  val _uploadState = mutableStateOf(UploadState())
    val uploadState = _uploadState

    fun createNotice(groupId: Long, title:String, content:String, fileList: MultipartBody.Part?){

        createNoticeUseCase(groupId,title,content,fileList!!).onEach {
        result ->
            when(result) {

                is Resource.Success -> {
                    _uploadState.value = UploadState(
                        isSuccess = true,
                        isLoading = false
                    )
                    Log.e("success in upload Notice",result.message.toString())

                }

                is Resource.Loading ->{
                    _uploadState.value = UploadState(
                        isSuccess = false,
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _uploadState.value = UploadState(
                        isSuccess = false,
                        isError = true
                    )
                    Log.e("error in upload Notice",result.message.toString())
                }

        }




        }.launchIn(viewModelScope)


    }


}