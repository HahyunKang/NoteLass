package com.app.note_lass.module.record.ui.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.DownloadStatusListener
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.group.data.CreateGroupState
import com.app.note_lass.module.record.data.DeleteExcelState
import com.app.note_lass.module.record.data.GetExcelState
import com.app.note_lass.module.record.data.GetGuidelineState
import com.app.note_lass.module.record.data.GetRecordContentState
import com.app.note_lass.module.record.data.GetScoreState
import com.app.note_lass.module.record.data.PostRecordContentState
import com.app.note_lass.module.record.data.RecordBody
import com.app.note_lass.module.record.domain.usecase.DeleteExcelFileUseCase
import com.app.note_lass.module.record.domain.usecase.GetExcelFileUseCase
import com.app.note_lass.module.record.domain.usecase.GetGuidelineUseCase
import com.app.note_lass.module.record.domain.usecase.GetRecordScoreUseCase
import com.app.note_lass.module.record.domain.usecase.GetRecordUseCase
import com.app.note_lass.module.record.domain.usecase.PostExcelUseCase
import com.app.note_lass.module.record.domain.usecase.PostRecordUseCase
import com.app.note_lass.module.student.data.HandBookListState
import com.app.note_lass.module.student.domain.usecase.getHandBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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
    val getHandBookListUseCase: getHandBookListUseCase,
    val deleteExcelUseCase : DeleteExcelFileUseCase,
    val getRecordScoreUseCase: GetRecordScoreUseCase,
    val getGuidelineUseCase: GetGuidelineUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DownloadStatusListener {

    var userId : Long = 0
    var studentName : String =""
    var studentId : Long  = 0
    private val _getRecordState = mutableStateOf(GetRecordContentState())
    val getRecordState = _getRecordState

    private val _handBookListState = mutableStateOf(HandBookListState())
    val getHandBookState = _handBookListState

    private val _postRecordState = mutableStateOf(PostRecordContentState())
    val postRecordState = _postRecordState

    private val _getExcelState = mutableStateOf(GetExcelState())
    val getExcelState= _getExcelState

    private val _getGuidelineState = mutableStateOf(GetGuidelineState())
    val getGuidelineState = _getGuidelineState

    private val _deleteExcelState = mutableStateOf(DeleteExcelState())
    val deleteExcelState = _deleteExcelState
    private val _getScoreState = mutableStateOf(GetScoreState())
    val getScoreState=  _getScoreState
    private var _downloadStatus = mutableStateOf("")
    val downloadStatus  = _downloadStatus
    init {
       userId = savedStateHandle.get<Long>("userId")!!
        Log.e("userId",userId.toString())
        getStudentHandBookList()
    }
    override fun onDownloadStatusUpdated(status: String) {
        _downloadStatus.value = status
    }

    fun setStatus(){
        _downloadStatus.value = "null"
    }
    private fun getStudentRecord(){
        getRecordUseCase(userId).onEach {
            result ->

                when (result) {

                    is Resource.Loading -> {
                        _getRecordState.value = GetRecordContentState(
                            isLoading = true,
                            isSuccess = false,
                            content = ""
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
                            content = ""
                        )
                    }
                }

        }.launchIn(viewModelScope)
    }

    fun postStudentRecord(recordBody: RecordBody){
        postRecordUseCase(userId,recordBody).onEach {
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

    private fun getStudentHandBookList(){
       getHandBookListUseCase(userId.toInt()).onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _handBookListState.value = HandBookListState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _handBookListState.value = HandBookListState(
                        isSuccess = true,
                        handBookList = result.data!!,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _handBookListState.value = HandBookListState(
                       isError = true
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun postExcel(file : MultipartBody.Part) {
        postExcelUseCase(file).onEach {
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
                    getStudentRecord()
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

    fun getExcel(downLoadExcel : () -> Unit){
        getExcelFileUseCase().onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _getExcelState.value = GetExcelState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _getExcelState.value = GetExcelState(
                        isLoading = false,
                        isSuccess = true,
                        excelUrl = result.data!!.fileUrl
                    )

                    downLoadExcel()
                    //deleteExcel()
                }

                is Resource.Error -> {
                    _getExcelState.value = GetExcelState(
                        isError = true
                    )
                }
            }

        }.launchIn(viewModelScope)
    }
    fun deleteExcel(){
        deleteExcelUseCase().onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _deleteExcelState.value = DeleteExcelState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _deleteExcelState.value = DeleteExcelState(
                       isSuccess = true,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _deleteExcelState.value = DeleteExcelState(
                        isError = true
                    )
                }
            }

        }.launchIn(viewModelScope)
    }
   fun getStudentScore(percentage:Int){
        getRecordScoreUseCase(userId,percentage).onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _getScoreState.value = GetScoreState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _getScoreState.value = GetScoreState(
                        isLoading = false,
                        isSuccess = true,
                        score = result.data!!
                    )
                }

                is Resource.Error -> {
                    _getScoreState.value = GetScoreState(
                        isError = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getGuideline(keywords : List<String>,handbookIds : List<Int>){
        getGuidelineUseCase(userId, keywords, handbookIds).onEach {
                result ->

            when (result) {

                is Resource.Loading -> {
                    _getGuidelineState.value = GetGuidelineState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {
                    _getGuidelineState.value = GetGuidelineState(
                        isLoading = false,
                        isSuccess = true,
                        guideLine = result.data!!
                    )
                }

                is Resource.Error -> {
                    _getGuidelineState.value = GetGuidelineState(
                        isError = true
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

}