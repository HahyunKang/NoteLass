package com.app.note_lass.module.student.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.common.Resources
import com.app.note_lass.module.group.data.groupList.GroupHashState
import com.app.note_lass.module.group.data.groupList.GroupListState
import com.app.note_lass.module.group.data.groupList.StudentHashState
import com.app.note_lass.module.group.data.studentList.StudentListState
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.group.domain.repository.GetStudentListUseCase
import com.app.note_lass.module.login.domain.LoginUseCase
import com.app.note_lass.module.student.data.HandBookRequest
import com.app.note_lass.module.student.data.HandBookSubmitState
import com.app.note_lass.module.student.domain.usecase.GetNoticeListUseCase
import com.app.note_lass.module.student.domain.usecase.PostHandBookUseCase
import com.app.note_lass.module.upload.data.notice.NoticeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.HashMap
import javax.inject.Inject


@HiltViewModel
class StudentNoticeListViewModel @Inject constructor(
    val getNoticeListUseCase: GetNoticeListUseCase,

    ) : ViewModel() {

    private val _noticeListState= mutableStateOf(RequestState<List<Resources>>())
    val noticeListState = _noticeListState



    init {
        getNoticeList()
    }

    fun getNoticeList(){
        getNoticeListUseCase().onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _noticeListState.value = RequestState(
                        isLoading = true,
                    )
                }
                is Resource.Success  ->{

                    _noticeListState.value = RequestState(
                        isLoading = true,
                        isSuccess = true,
                        result = result.data
                    )
                    Log.e("success in studentList",result.code.toString())
                }
                is Resource.Error -> {
                    _noticeListState.value = RequestState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)


    }

}