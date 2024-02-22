package com.app.note_lass.module.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.groupList.GroupListState
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.home.material.MaterialFile
import com.app.note_lass.module.note.data.Note
import com.app.note_lass.module.note.domain.GetFileUsecase
import com.app.note_lass.module.note.domain.GetLatestNoteUsecase
import com.app.note_lass.module.note.domain.GetLatestUploadMaterialUsecase
import com.app.note_lass.module.upload.data.Material.Material
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getLatestUploadMaterialUsecase: GetLatestUploadMaterialUsecase,
    val getLatestNoteUsecase: GetLatestNoteUsecase,
    val getMaterialFile : GetFileUsecase,
    val getGroupUseCase: GetGroupUseCase,
    ) : ViewModel(){

    private val _getLatestUploadMaterialState = mutableStateOf(RequestState<List<Material>>())
    val getLatestUploadMaterialState = _getLatestUploadMaterialState
    private val _getMaterialFileState = mutableStateOf(RequestState<MaterialFile>())
    val getMaterialFileState = _getMaterialFileState
    private val _getLatestNoteState = mutableStateOf(RequestState<List<Note>>())
    val getLatestNoteState= _getLatestNoteState
    private val _groupListState = mutableStateOf(GroupListState())
    val groupListState = _groupListState
   init {
       Log.e("viewModel LifeCycle","Test")
       getGroupList()
   }
    fun getLatestMaterial() {
        getLatestUploadMaterialUsecase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _getLatestUploadMaterialState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _getLatestUploadMaterialState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result = result.data
                    )
                }

                is Resource.Error -> {
                    _getLatestUploadMaterialState.value = RequestState(
                        isError = true,
                    )
                }


            }
        }.launchIn(viewModelScope)

    }

    fun getLatestNote() {
        getLatestNoteUsecase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _getLatestNoteState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _getLatestNoteState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result = result.data
                    )
                }

                is Resource.Error -> {
                    _getLatestNoteState.value = RequestState(
                        isError = true,
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

    fun getGroupList(){
        getGroupUseCase().onEach{
                result ->
            when(result) {
                is Resource.Loading -> {
                    _groupListState.value = GroupListState(
                        isLoading = true,
                        isSuccess = false
                    )
                }
                is Resource.Success  ->{
                    _groupListState.value = GroupListState(
                        isSuccess = true,
                        isError = false,
                        groupList = result.data!!
                    )
                    Log.e("groupListData",result.toString())
                }
                is Resource.Error -> {
                    _groupListState.value = GroupListState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }

}