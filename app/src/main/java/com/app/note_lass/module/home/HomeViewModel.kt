package com.app.note_lass.module.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.groupList.GroupHashState
import com.app.note_lass.module.note.data.Note
import com.app.note_lass.module.note.domain.GetLatestNoteUsecase
import com.app.note_lass.module.note.domain.GetLatestUploadMaterialUsecase
import com.app.note_lass.module.upload.data.Material.Material
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getLatestUploadMaterialUsecase: GetLatestUploadMaterialUsecase,
    val getLatestNoteUsecase: GetLatestNoteUsecase
) : ViewModel(){

    private val _getLatestUploadMaterialState = mutableStateOf(RequestState<List<Material>>())
    val getLatestUploadMaterialState = _getLatestUploadMaterialState

    private val _getLatestNoteState = mutableStateOf(RequestState<List<Note>>())
    val getLatestNoteState= _getLatestNoteState

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
}