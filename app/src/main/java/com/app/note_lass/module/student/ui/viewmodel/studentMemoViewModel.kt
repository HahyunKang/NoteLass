package com.app.note_lass.module.student.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.groupList.GroupHashState
import com.app.note_lass.module.group.data.groupList.GroupListState
import com.app.note_lass.module.group.data.groupList.StudentHashState
import com.app.note_lass.module.group.data.studentList.StudentListState
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.group.domain.repository.GetStudentListUseCase
import com.app.note_lass.module.login.domain.LoginUseCase
import com.app.note_lass.module.student.data.HandBookRequest
import com.app.note_lass.module.student.data.HandBookSubmitState
import com.app.note_lass.module.student.domain.usecase.PostHandBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.HashMap
import javax.inject.Inject


@HiltViewModel
class StudentMemoViewModel @Inject constructor(
    val getGroupUseCase: GetGroupUseCase,
    val getStudentUseCase: GetStudentListUseCase,
    val postHandBookUseCase: PostHandBookUseCase
) : ViewModel() {

    private val _groupHashState = mutableStateOf(GroupHashState())
    val groupHashState = _groupHashState

    private val _studentHashState = mutableStateOf(StudentHashState())
    val studentHashState = _studentHashState

    private val _handBookSubmitState = mutableStateOf(HandBookSubmitState())
    val handBookSubmitState = _handBookSubmitState

    init {
        getGroupList()
    }
    fun getGroupList() {
        getGroupUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _groupHashState.value = GroupHashState(
                        isLoading = true,
                        isSuccess = false
                    )
                }

                is Resource.Success -> {

                    val groupList = result.data!!
                    val groupHash: HashMap<String, Int> = hashMapOf()
                    groupList.map {
                        groupHash["${it.grade}학년 ${it.classNum}반 ${it.subject}"] = it.id.toInt()
                    }

                    _groupHashState.value = GroupHashState(
                        isSuccess = true,
                        isError = false,
                        groupHash = groupHash
                    )
                    Log.e("groupListData", result.toString())
                }

                is Resource.Error -> {
                    _groupHashState.value = GroupHashState(
                        isError = true
                    )
                }


            }
        }.launchIn(viewModelScope)

    }

    fun getStudentList(id : Int){
        getStudentUseCase(id).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _studentHashState.value = StudentHashState(
                        isLoading = true,
                    )
                }
                is Resource.Success  ->{

                    val studentList = result.data!!
                    val studentHash: HashMap<String, Int> = hashMapOf()
                    studentList.map {
                        studentHash[it.name] = it.id
                    }
                    _studentHashState.value = StudentHashState(
                        isLoading = false,
                        isSuccess = true,
                        studentHash =studentHash
                    )
                    Log.e("success in studentList",result.code.toString())
                }
                is Resource.Error -> {
                    _studentHashState.value = StudentHashState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)


    }

    fun postHandBook(groupId : Int, userId: Int, handBookRequest: HandBookRequest, onSuccess : () -> Unit){
        postHandBookUseCase(groupId,userId,handBookRequest).onEach {
                result ->
            when(result) {
                is Resource.Loading -> {
                    _handBookSubmitState.value = HandBookSubmitState(
                        isLoading = true,
                    )
                }
                is Resource.Success  ->{

                    _handBookSubmitState.value = HandBookSubmitState(
                        isSuccess = true,
                    )
                    onSuccess()
                    Log.e("success in handBook",result.code.toString())
                }
                is Resource.Error -> {
                    _handBookSubmitState.value = HandBookSubmitState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)


    }
}