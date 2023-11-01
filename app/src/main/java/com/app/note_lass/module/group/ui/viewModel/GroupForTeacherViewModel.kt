package com.app.note_lass.module.group.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.CreateGroupState
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupList.GroupListState
import com.app.note_lass.module.group.data.studentList.StudentListState
import com.app.note_lass.module.group.domain.repository.CreateGroupUseCase
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.group.domain.repository.GetStudentListUseCase
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupForTeacherViewModel @Inject constructor(
    val getStudentUseCase : GetStudentListUseCase,
    val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _studentListState = mutableStateOf(StudentListState())
    val studentListState = _studentListState

    init{
        val groupId = savedStateHandle.get<Int>("groupId")
        if (groupId != null) {
            getStudentList(groupId)
        }
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


}