package com.app.note_lass.module.upload.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.upload.data.notice.NoticeDetailState
import com.app.note_lass.module.upload.data.notice.toDetail
import com.app.note_lass.module.group.domain.repository.GetNoticeDetailUseCase
import com.app.note_lass.module.home.material.MaterialFile
import com.app.note_lass.module.note.domain.GetFileUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
   val noticeDetailUseCase: GetNoticeDetailUseCase,
   val getMaterialFile : GetFileUsecase,
   savedStateHandle: SavedStateHandle
) : ViewModel(){

    @RequiresApi(Build.VERSION_CODES.O)
    private var _noticeDetailState = mutableStateOf(NoticeDetailState())
    @RequiresApi(Build.VERSION_CODES.O)
    val noticeDetailState = _noticeDetailState
    private val _getMaterialFileState = mutableStateOf(RequestState<MaterialFile>())
    val getMaterialFileState = _getMaterialFileState
    init {
        val noticeId= savedStateHandle.get<Long>("dashboardId")
        val type = savedStateHandle.get<String?>("type")

        noticeId?.let {
            Log.e("dashboardId",it.toString())
            if(type != "material")getNoticeDetail(noticeId)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
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

}