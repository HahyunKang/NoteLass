package com.app.note_lass.module.student.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.common.Resources
import com.app.note_lass.module.dashboard.domain.GetDashBoardsInGroupUseCase
import com.app.note_lass.module.home.tab.notice.DashBoard
import com.app.note_lass.module.student.domain.usecase.GetNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DashBoardListViewModel @Inject constructor(
    val getNoticeListUseCase: GetNoticeListUseCase,
    val getDashBoardsInGroupUseCase: GetDashBoardsInGroupUseCase,
    ) : ViewModel() {

    private val _noticeListState= mutableStateOf(RequestState<List<Resources>>())
    val noticeListState = _noticeListState
    private val _dashBoardsState = mutableStateOf(RequestState<List<DashBoard>>())
    val dashBoardState = _dashBoardsState


    init {
        getNoticeList()
        getDashboards()
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
    private fun getDashboards(){
        getDashBoardsInGroupUseCase().onEach{
                result ->
            when(result) {
                is Resource.Loading -> {
                    _dashBoardsState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }
                is Resource.Success  ->{

                    _dashBoardsState.value = RequestState(
                        isSuccess = true,
                        isError = false,
                        result = result.data
                    )
                    Log.e("groupListData",result.toString())
                }
                is Resource.Error -> {
                    _dashBoardsState.value = RequestState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }
}