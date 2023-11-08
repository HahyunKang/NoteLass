package com.app.note_lass.module.group.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.upload.notice.NoticeDetailState
import com.app.note_lass.module.group.domain.repository.GetNoticeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
   val noticeDetailUseCase: GetNoticeDetailUseCase
) : ViewModel(){

    private var _noticeDetailState = mutableStateOf(NoticeDetailState())
    val noticeDetailState = _noticeDetailState
    fun getNoticeDetail(noticeId : Long){
        noticeDetailUseCase(noticeId).onEach {

            result ->
            when(result){

                is Resource.Success -> {
                    _noticeDetailState.value = NoticeDetailState(
                        isSuccess = true,
                        isLoading = false
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