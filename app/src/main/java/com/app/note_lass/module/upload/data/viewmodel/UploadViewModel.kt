package com.app.note_lass.module.upload.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.upload.data.UploadState
import com.app.note_lass.module.group.domain.repository.CreateNoticeUseCase
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.upload.domain.PostMaterialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    val createNoticeUseCase: CreateNoticeUseCase,
    val  makeMaterialUseCase: PostMaterialUseCase
) : ViewModel(){

    private  val _uploadState = mutableStateOf(UploadState())
    val uploadState = _uploadState

    private val _makeMaterialState = mutableStateOf(RequestState<Nothing>())
    val makeMaterialState = _makeMaterialState

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

    fun makeMaterial(groupId: Long, noteRequest: NoteRequest, fileList : MultipartBody.Part) {
        makeMaterialUseCase(groupId, noteRequest,fileList).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _makeMaterialState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _makeMaterialState.value = RequestState(
                        isLoading = false,
                        isSuccess = true
                    )
                }

                is Resource.Error -> {
                    _makeMaterialState.value = RequestState(
                        isError = true
                    )
                }


            }
        }.launchIn(viewModelScope)

    }


}