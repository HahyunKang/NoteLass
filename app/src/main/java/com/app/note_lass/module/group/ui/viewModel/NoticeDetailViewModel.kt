package com.app.note_lass.module.group.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.upload.notice.Notice
import com.app.note_lass.module.group.data.upload.notice.NoticeDetail
import com.app.note_lass.module.group.data.upload.notice.NoticeDetailState
import com.app.note_lass.module.group.data.upload.notice.toDetail
import com.app.note_lass.module.group.domain.repository.GetNoticeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
   val noticeDetailUseCase: GetNoticeDetailUseCase,
   savedStateHandle: SavedStateHandle
) : ViewModel(){

    private var _noticeDetailState = mutableStateOf(NoticeDetailState())
    val noticeDetailState = _noticeDetailState

    init {
        val noticeId= savedStateHandle.get<Long>("noticeId")
        noticeId?.let {
            getNoticeDetail(noticeId)
        }
    }


    fun getNoticeDetail(noticeId : Long){
        noticeDetailUseCase(noticeId).onEach {

            result ->
            when(result){

                is Resource.Success -> {
                    _noticeDetailState.value = NoticeDetailState(
                        isSuccess = true,
                        isLoading = false,
                        noticeDetail = result.data!!.toDetail()
                    )

                }
                is Resource.Loading -> {
                    _noticeDetailState.value = NoticeDetailState(
                        isSuccess = false,
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                _noticeDetailState.value = NoticeDetailState(
                   isError = true
                )
            }

            }


        }.launchIn(viewModelScope)
    }

}