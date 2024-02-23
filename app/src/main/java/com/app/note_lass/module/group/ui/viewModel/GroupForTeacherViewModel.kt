package com.app.note_lass.module.group.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.CreateGroupState
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupInfo
import com.app.note_lass.module.group.data.groupList.GroupListState
import com.app.note_lass.module.group.data.join.ApproveOrRejectGroupState
import com.app.note_lass.module.group.data.join.JoinStudentListState
import com.app.note_lass.module.group.data.studentList.StudentListState
import com.app.note_lass.module.group.domain.repository.ApproveAllGroupUseCase
import com.app.note_lass.module.group.domain.repository.ApproveGroupUseCase
import com.app.note_lass.module.group.domain.repository.CreateGroupUseCase
import com.app.note_lass.module.group.domain.repository.DeleteGroupUseCase
import com.app.note_lass.module.group.domain.repository.DeleteStudentUseCase
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.group.domain.repository.GetJoinStudentListUseCase
import com.app.note_lass.module.group.domain.repository.GetStudentListUseCase
import com.app.note_lass.module.group.domain.repository.RejectGroupUseCase
import com.app.note_lass.module.record.data.Evaluations
import com.app.note_lass.module.record.domain.usecase.GetStudentEvaluationsUseCase
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupForTeacherViewModel @Inject constructor(
    val getStudentUseCase : GetStudentListUseCase,
    val approveGroupUseCase: ApproveGroupUseCase,
    val approveAllGroupUseCase: ApproveAllGroupUseCase,
    val rejectGroupUseCase: RejectGroupUseCase,
    val joinStudentListUseCase: GetJoinStudentListUseCase,
    val deleteGroupUseCase: DeleteGroupUseCase,
    val deleteStudentUseCase: DeleteStudentUseCase,
    val getStudentEvaluationsUseCase: GetStudentEvaluationsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _studentListState = mutableStateOf(StudentListState())
    val studentListState = _studentListState

    private val _joinStudentListState = mutableStateOf(JoinStudentListState())
    val joinStudentListState = _joinStudentListState

    private val _approveOrReject = mutableStateOf(ApproveOrRejectGroupState())
    val approveOrReject = _approveOrReject

    private val _deleteGroupState = mutableStateOf(RequestState<Nothing>())
    val deleteGroupState = _deleteGroupState

    private val _deleteStudent = mutableStateOf(RequestState<Nothing>())
    val deleteStudentState= _deleteStudent

    private val _getEvaluationsState  = mutableStateOf(RequestState<List<Evaluations>>())
    val getEvaluationState = _getEvaluationsState

    private val groupId = mutableStateOf(0)

    init{
        groupId.value = savedStateHandle.get<Int>("groupId")!!

        getStudentList(groupId.value)
    }

    fun getStudentList( id : Int){
       getStudentUseCase(id).onEach {
            result ->
            when(result) {
                is Resource.Loading -> {
                    _studentListState.value = StudentListState(
                        isLoading = true,
                    )
                }
                is Resource.Success  ->{
                    _studentListState.value = StudentListState(
                        isLoading = false,
                        isSuccess = true,
                        studentList = result.data!!
                    )
                    Log.e("success in studentList",result.code.toString())
                }
                is Resource.Error -> {
                    _studentListState.value = StudentListState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }
    fun getJoinStudentList(){
        joinStudentListUseCase(groupId.value.toLong()).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _joinStudentListState.value = JoinStudentListState(
                        isLoading = true,
                    )
                }
                is Resource.Success  ->{
                    _joinStudentListState.value = JoinStudentListState(
                        isLoading = false,
                        isSuccess = true,
                        groupInfo = result.data?.groupInfo!!,
                        groupCode = result.data.groupCode,
                        joinStudentList = result.data.applyDtos
                    )
                    Log.e("success in JoinstudentList",result.code.toString())
                    Log.e("success in JoinstudentList",result.data.groupInfo)
                    Log.e("success in JoinstudentList",result.data.applyDtos.size.toString())

                }
                is Resource.Error -> {
                    _joinStudentListState.value = JoinStudentListState(
                        isError = true,
                    )
                    Log.e("Error in JoinstudentList",result.code.toString())

                }
            }


        }.launchIn(viewModelScope)
    }
    fun rejectGroup(userId : Long){
        rejectGroupUseCase(groupId.value.toLong(),userId).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                   _approveOrReject.value = ApproveOrRejectGroupState(
                       isLoading = true,
                       isSuccess = false
                   )
                }
                is Resource.Success  ->{
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isLoading = false,
                        isSuccess = true,
                        isApprove = false
                    )
                    Log.e("success in reject",result.code.toString())

                }
                is Resource.Error -> {
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isError =  true,
                        isSuccess = false,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }

    fun approveGroup(userId :Long, isToast : () -> Unit){
        approveGroupUseCase(groupId.value.toLong(),userId).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isLoading  =  true,
                        isSuccess = false,
                    )
                }
                is Resource.Success  ->{
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isSuccess = true,
                        isApprove = true,
                        isLoading = false,
                    )
                    isToast()
                    Log.e("success in approve",result.code.toString())
                }
                is Resource.Error -> {
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isError =  true,
                        isSuccess = false,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }
    fun approveAllGroup(isToast : () -> Unit){
        approveAllGroupUseCase(groupId.value.toLong()).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isLoading  =  true,
                        isSuccess = false,
                    )
                }
                is Resource.Success  ->{
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isSuccess = true,
                        isApprove = true,
                        isLoading = false,
                    )
                    isToast()
                    Log.e("success in approve",result.code.toString())
                }
                is Resource.Error -> {
                    _approveOrReject.value = ApproveOrRejectGroupState(
                        isError =  true,
                        isSuccess = false,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }
    fun deleteGroup(){
        deleteGroupUseCase(groupId.value.toLong()).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _deleteGroupState.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }
                is Resource.Success  ->{
                    _deleteGroupState.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )
                    Log.e("success in reject",result.code.toString())

                }
                is Resource.Error -> {
                    _deleteGroupState.value = RequestState(
                        isError =  true,
                        isSuccess = false,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }

    fun deleteStudent(userId : Long){
        deleteStudentUseCase(groupId.value.toLong(),userId).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _deleteStudent.value = RequestState(
                        isLoading = true,
                        isSuccess = false
                    )
                }
                is Resource.Success  ->{
                    _deleteStudent.value = RequestState(
                        isLoading = false,
                        isSuccess = true,
                    )
                    Log.e("success in reject",result.code.toString())

                }
                is Resource.Error -> {
                    _deleteStudent.value = RequestState(
                        isError =  true,
                        isSuccess = false,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }


}