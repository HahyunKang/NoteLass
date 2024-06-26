package com.app.note_lass.module.dashboard.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.dashboard.data.UploadState
import com.app.note_lass.module.dashboard.domain.notice.CreateNoticeUseCase
import com.app.note_lass.module.dashboard.domain.notice.ModifyNoticeUseCase
import com.app.note_lass.module.home.material.MaterialFile
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.note.domain.GetFileUsecase
import com.app.note_lass.module.note.domain.ModifyMaterialUseCase
import com.app.note_lass.module.dashboard.domain.material.PostMaterialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    val createNoticeUseCase: CreateNoticeUseCase,
    val makeMaterialUseCase: PostMaterialUseCase,
    val getMaterialFile : GetFileUsecase,
    val modifyNoticeUseCase: ModifyNoticeUseCase,
    val modifyMaterialUseCase: ModifyMaterialUseCase,
    val savedStateHandle: SavedStateHandle
    ) : ViewModel(){

    private  val _uploadState = mutableStateOf(UploadState())
    val uploadState = _uploadState

    private val _makeMaterialState = mutableStateOf(RequestState<Nothing>())
    val makeMaterialState = _makeMaterialState

    private val _getMaterialFileState = mutableStateOf(RequestState<MaterialFile>())
    val getMaterialFileState = _getMaterialFileState

    private  val _modifyState = mutableStateOf(RequestState<Nothing>())
    val modifyState = _modifyState

    private var dashboardId  : Long = 0
    init {
        dashboardId= savedStateHandle.get<Long>("dashboardId") ?: 0

    }

    fun createNotice(groupId: Long, title:String, content:String, fileList: List<MultipartBody.Part?>){

        createNoticeUseCase(groupId, title, content, fileList!!).onEach {
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

    fun getFile(fileId : Long) {
        getMaterialFile(fileId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _getMaterialFileState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _getMaterialFileState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result = MaterialFile(fileId = fileId, stream =result.data!!)
                    )
                }

                is Resource.Error -> {

                    _getMaterialFileState.value = RequestState(
                        isError = true,
                    )
                }


            }
        }.launchIn(viewModelScope)

    }
    fun modifyNotice(groupId:Long,title:String,content:String,ids:List<Long>,fileList: List<MultipartBody.Part>) {
        modifyNoticeUseCase(groupId,dashboardId,title,content,ids,fileList).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _modifyState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _modifyState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )
                }

                is Resource.Error -> {

                    _modifyState.value = RequestState(
                        isError = true,
                    )
                }


            }
        }.launchIn(viewModelScope)

    }

    fun modifyMaterial(
        groupId:Long,
        title:String,
        content:String,
        ids:List<Long>,
        fileList: MultipartBody.Part?
    ) {
        modifyMaterialUseCase(groupId, dashboardId, title, content, ids, fileList).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _modifyState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _modifyState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )
                }

                is Resource.Error -> {

                    _modifyState.value = RequestState(
                        isError = true,
                    )
                }


            }
        }.launchIn(viewModelScope)

    }

}