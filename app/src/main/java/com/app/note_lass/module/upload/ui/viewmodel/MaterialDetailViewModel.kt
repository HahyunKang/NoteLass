package com.app.note_lass.module.upload.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.domain.repository.GetMaterialDetailUseCase
import com.app.note_lass.module.upload.data.notice.NoticeDetailState
import com.app.note_lass.module.upload.data.notice.toDetail
import com.app.note_lass.module.group.domain.repository.GetNoticeDetailUseCase
import com.app.note_lass.module.home.material.MaterialFile
import com.app.note_lass.module.note.domain.GetFileUsecase
import com.app.note_lass.module.upload.data.notice.MaterialDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MaterialDetailViewModel @Inject constructor(
    val getMaterialDetailUseCase: GetMaterialDetailUseCase,
   val getMaterialFile : GetFileUsecase,
   savedStateHandle: SavedStateHandle
) : ViewModel(){

    @RequiresApi(Build.VERSION_CODES.O)
    private var _materialDetailState = mutableStateOf(RequestState<MaterialDetail>())
    @RequiresApi(Build.VERSION_CODES.O)
    val materialDetailState = _materialDetailState
    private val _getMaterialFileState = mutableStateOf(RequestState<MaterialFile>())
    val getMaterialFileState = _getMaterialFileState
    init {
        val materialId= savedStateHandle.get<Long>("dashboardId")
        val type = savedStateHandle.get<String?>("type")
        materialId?.let {
          if(type != "notice") getMaterialDetail(materialId)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getMaterialDetail(materialId : Long){
        getMaterialDetailUseCase(materialId).onEach {

            result ->
            when(result){

                is Resource.Success -> {
                    _materialDetailState.value = RequestState(
                        isSuccess = true,
                        isLoading = false,
                        result = result.data!!.toDetail()
                    )

                }
                is Resource.Loading -> {
                    _materialDetailState.value = RequestState(
                        isSuccess = false,
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _materialDetailState.value = RequestState(
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