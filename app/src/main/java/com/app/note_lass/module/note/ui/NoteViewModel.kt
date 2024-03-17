package com.app.note_lass.module.note.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.groupList.GroupHashState
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.home.material.MaterialFile
import com.app.note_lass.module.note.data.Note
import com.app.note_lass.module.note.data.NoteAccessedDto
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.note.domain.AccessNoteUsecase
import com.app.note_lass.module.note.domain.DeleteNoteUsecase
import com.app.note_lass.module.note.domain.GetFileUsecase
import com.app.note_lass.module.note.domain.GetLatestNoteUsecase
import com.app.note_lass.module.note.domain.GetMaterialToNoteUseCase
import com.app.note_lass.module.note.domain.GetNoteListUseCase
import com.app.note_lass.module.upload.data.Material.Material
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
    val  makeMaterialUseCase: PostMaterialUseCase,
    val getMaterialToNoteUseCase: GetMaterialToNoteUseCase,
    val getLatestNoteUsecase: GetLatestNoteUsecase,
    val getMaterialFile : GetFileUsecase,
    val accessNoteUsecase: AccessNoteUsecase,
    val deleteNoteUsecase: DeleteNoteUsecase
    ): ViewModel() {

    private var _notesState = mutableStateListOf<Note>()
    val notesState =_notesState

    private val _groupHashState = mutableStateOf(GroupHashState())
    val groupHashState = _groupHashState


    private val _noteListState = mutableStateOf(RequestState<List<Note>>())
    val noteListState = _noteListState


    private val _makeMaterialState = mutableStateOf(RequestState<Nothing>())
    val makeMaterialState = _makeMaterialState

    private val _getMaterialToNoteState = mutableStateOf(RequestState<Nothing>())
    val getMaterialToNoteState = _getMaterialToNoteState

    private val _accessNoteState = mutableStateOf(RequestState<NoteAccessedDto>())
    val accessNoteState  = _accessNoteState

    private val _getMaterialFileState = mutableStateOf(RequestState<MaterialFile>())
    val getMaterialFileState = _getMaterialFileState


    private val _deleteNoteState = mutableStateOf(RequestState<Nothing>())
    val deleteNoteState = _deleteNoteState

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
                        isSuccess = true,
                        result = emptyList()
                    )
                }

                is Resource.Success -> {
                    _noteListState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result = result.data
                    )
                    result.data?.let { _notesState.addAll(it) }
                }

                is Resource.Error -> {
                    _noteListState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)


    }

    fun makeMaterial(groupId: Long, noteRequest: NoteRequest, fileList: MultipartBody.Part) {
        makeMaterialUseCase(groupId, noteRequest, fileList).onEach { result ->
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


    fun makeMaterialToNote(materialId: Long) {
        getMaterialToNoteUseCase(materialId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _getMaterialToNoteState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _getMaterialToNoteState.value = RequestState(
                        isLoading = false,
                        isSuccess = true
                    )
                }

                is Resource.Error -> {
                    _getMaterialToNoteState.value = RequestState(
                        isError = true
                    )
                }


            }
        }.launchIn(viewModelScope)

    }

    fun accessNote(noteId: Long) {
        accessNoteUsecase(noteId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _accessNoteState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _accessNoteState.value = RequestState(
                        isLoading = false,
                        isSuccess = true
                    )
                }

                is Resource.Error -> {
                    _accessNoteState.value = RequestState(
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

    fun deleteNote(id : Long) {
        deleteNoteUsecase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _deleteNoteState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _deleteNoteState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )
                    deleteNoteById(id)
                }

                is Resource.Error -> {
                    _deleteNoteState.value = RequestState(
                        isError = true,
                    )
                }


            }
        }.launchIn(viewModelScope)

    }


    fun deleteNoteById(noteId : Long){
        _notesState.removeIf { it.id == noteId }
    }
}