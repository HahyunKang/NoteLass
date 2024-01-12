package com.app.note_lass.module.note.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.groupList.GroupHashState
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.note.data.Note
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.note.domain.GetNoteListUseCase
import com.app.note_lass.module.upload.domain.PostMaterialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val getGroupUseCase: GetGroupUseCase,
    val  noteListUseCase : GetNoteListUseCase,
    val  makeMaterialUseCase: PostMaterialUseCase
    ): ViewModel() {


    private val _groupHashState = mutableStateOf(GroupHashState())
    val groupHashState = _groupHashState


    private val _noteListState = mutableStateOf(RequestState<List<Note>>())
    val noteListState = _noteListState


    private val _makeMaterialState = mutableStateOf(RequestState<Nothing>())
    val makeMaterialState = _makeMaterialState
    init {
        getNoteList()
        getGroupList()
    }

    fun getGroupList() {
        getGroupUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _groupHashState.value = GroupHashState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {

                    val groupList = result.data!!
                    val groupHash: HashMap<String, Int> = hashMapOf()
                    groupList.map {
                        groupHash["${it.grade}학년 ${it.classNum}반 ${it.subject}"] = it.id.toInt()
                    }

                    _groupHashState.value = GroupHashState(
                        isSuccess = true,
                        isError = false,
                        groupHash = groupHash
                    )
                }

                is Resource.Error -> {
                    _groupHashState.value = GroupHashState(
                        isError = true
                    )
                }


            }
        }.launchIn(viewModelScope)

    }

    fun getNoteList() {
        noteListUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _noteListState.value = RequestState(
                        isLoading = true,
                        isSuccess = true
                    )
                }

                is Resource.Success -> {
                    _noteListState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result = result.data
                    )
                }

                is Resource.Error -> {
                    _noteListState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)


    }

    fun makeMaterial(groupId: Long, noteRequest: NoteRequest,fileList : MultipartBody.Part) {
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
                    getNoteList()
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