package com.app.note_lass.module.group.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.studentList.StudentListState
import com.app.note_lass.module.group.data.upload.notice.NoticeListDto
import com.app.note_lass.module.group.data.upload.notice.NoticeListState
import com.app.note_lass.module.group.domain.repository.GetNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class GroupForStudentViewModel @Inject constructor(
   val getNoticeListUseCase: GetNoticeListUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val groupId = mutableStateOf(0)
    private val _studentNoticeState = mutableStateOf(NoticeListState())
    val studentNoticeState = _studentNoticeState
    init{
        groupId.value = savedStateHandle.get<Int>("groupId")!!
        getNoticeList()
    }

    private fun getNoticeList(){
        getNoticeListUseCase(groupId.value.toLong()).onEach {
            result ->
            when(result) {

                is Resource.Success -> {
                    _studentNoticeState.value = NoticeListState(
                        isLoading = false,
                        isSuccess = true,
                        noticeList = result.data!!
                    )
                }
                is Resource.Loading-> {
                    _studentNoticeState.value = NoticeListState(
                        isLoading = true,
                        isSuccess = false,
                    )
                }

                is Resource.Error -> {
                    _studentNoticeState.value = NoticeListState(
                        isError = true,
                        isSuccess = false,
                    )
                    Log.e("error in noticeList", result.message.toString())
                }

            }
        }.launchIn(viewModelScope)

    }


}