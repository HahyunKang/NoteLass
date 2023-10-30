package com.app.note_lass.module.group.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.group.data.CreateGroupState
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupList.GroupListState
import com.app.note_lass.module.group.domain.repository.CreateGroupUseCase
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    val createGroupUseCase: CreateGroupUseCase,
    val getGroupUseCase: GetGroupUseCase
) : ViewModel(){

    private val _groupCreateState = mutableStateOf(CreateGroupState())
    val groupCreateGroupState = _groupCreateState

    private val _groupListState  = mutableStateOf(GroupListState())
    val groupListState = _groupListState
    fun createGroup(
        createRequest : InfoForCreate
    ){
        createGroupUseCase(createRequest).onEach {
            result ->
            when(result) {
                is Resource.Loading -> {
                    _groupListState.value = GroupListState(
                        isLoading = true,
                    )
                }
                is Resource.Success  ->{
                    _groupListState.value = GroupListState(
                        isLoading = false,
                        isSuccess = true,)
                }
                is Resource.Error -> {
                    _groupCreateState.value = CreateGroupState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }


    fun getGroupList(
    ){
        getGroupUseCase().onEach{
                result ->
            when(result) {
                is Resource.Loading -> {
                    _groupCreateState.value = CreateGroupState(
                        isLoading = true,
                    )
                }
                is Resource.Success  ->{
                    _groupCreateState.value = CreateGroupState(
                        isSuccess = true,
                        isLoading = false,
                        code = result.data.toString().toInt()
                    )
                }
                is Resource.Error -> {
                    _groupCreateState.value = CreateGroupState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }

}