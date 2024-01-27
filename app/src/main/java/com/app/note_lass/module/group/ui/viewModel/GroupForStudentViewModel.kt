package com.app.note_lass.module.group.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.upload.data.notice.NoticeListState
import com.app.note_lass.module.group.domain.repository.GetNoticeListUseCase
import com.app.note_lass.module.student.domain.usecase.GetMaterialListUseCase
import com.app.note_lass.module.upload.data.Material.Material
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class GroupForStudentViewModel @Inject constructor(
   val getNoticeListUseCase: GetNoticeListUseCase,
   val getMaterialListUseCase: GetMaterialListUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val groupId = mutableStateOf(0)
    private val _studentNoticeState = mutableStateOf(NoticeListState())
    val studentNoticeState = _studentNoticeState

    private val _studentMaterialState = mutableStateOf(RequestState<List<Material>>(result = emptyList()))
    val studentMaterialState =_studentMaterialState
    init{
        groupId.value = savedStateHandle.get<Int>("groupId")!!
        Log.e("groupId",groupId.value.toString())
        getNoticeList()
        getMaterialList()
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

    private fun getMaterialList(){
        getMaterialListUseCase(groupId.value.toLong()).onEach {
                result ->
            when(result) {

                is Resource.Success -> {
                    _studentMaterialState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                        result= result.data!!
                    )
                }
                is Resource.Loading-> {
                    _studentMaterialState.value = RequestState(
                        isLoading = true,
                        isSuccess = false,
                        result = emptyList()
                    )
                }

                is Resource.Error -> {
                    _studentMaterialState.value = RequestState(
                        isError = true
                    )
                    Log.e("error in noticeList", result.message.toString())
                }

            }
        }.launchIn(viewModelScope)

    }


}