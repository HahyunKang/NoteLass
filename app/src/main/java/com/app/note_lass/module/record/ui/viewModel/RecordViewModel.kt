package com.app.note_lass.module.record.ui.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.group.data.CreateGroupState
import com.app.note_lass.module.record.data.GetRecordContentState
import com.app.note_lass.module.record.data.PostRecordContentState
import com.app.note_lass.module.record.data.RecordBody
import com.app.note_lass.module.record.domain.usecase.GetExcelFileUseCase
import com.app.note_lass.module.record.domain.usecase.GetRecordUseCase
import com.app.note_lass.module.record.domain.usecase.PostExcelUseCase
import com.app.note_lass.module.record.domain.usecase.PostRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class RecordViewModel @Inject constructor(
    val getRecordUseCase: GetRecordUseCase,
    val postRecordUseCase: PostRecordUseCase,
    val postExcelUseCase: PostExcelUseCase,
    val getExcelFileUseCase: GetExcelFileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    var userId : Long = 0
    var studentName : String =""
    var studentId : Long  = 0
    private val _getRecordState = mutableStateOf(GetRecordContentState())
    val getRecordState = _getRecordState

    private val _postRecordState = mutableStateOf(PostRecordContentState())
    val postRecordState = _postRecordState

    init {
       userId = savedStateHandle.get<Long>("userId")!!
        Log.e("userId",userId.toString())
    }

    fun getStudentRecord(groupId : Long){
        getRecordUseCase(groupId, userId).onEach {
            result ->

                when (result) {

                    is Resource.Loading -> {
                        _getRecordState.value = GetRecordContentState(
                            isLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        _getRecordState.value = GetRecordContentState(
                            isLoading = false,
                            isSuccess = true,
                            content = result.data!!
                        )
                    }

                    is Resource.Error -> {
                        _getRecordState.value = GetRecordContentState(
                            isError = true,
                        )
                    }
                }

        }.launchIn(viewModelScope)
    }

    fun postStudentRecord(groupId : Long ,recordBody: RecordBody){
        postRecordUseCase(groupId, userId,recordBody).onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _postRecordState.value = PostRecordContentState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _postRecordState.value = PostRecordContentState(
                        isLoading = false,
                        isSuccess = true,
                    )
                }

                is Resource.Error -> {
                    _postRecordState.value = PostRecordContentState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun postExcel(groupId : Long,file : MultipartBody.Part) {
        postExcelUseCase(groupId, file).onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _postRecordState.value = PostRecordContentState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _postRecordState.value = PostRecordContentState(
                        isLoading = false,
                        isSuccess = true,
                    )
                }

                is Resource.Error -> {
                    _postRecordState.value = PostRecordContentState(
                        isError = true,
                        isSuccess = false
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getExcel(groupId : Long){
        getExcelFileUseCase(groupId).onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _getRecordState.value = GetRecordContentState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _getRecordState.value = GetRecordContentState(
                        isLoading = false,
                        isSuccess = true,
                    )
                }

                is Resource.Error -> {
                    _getRecordState.value = GetRecordContentState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

}