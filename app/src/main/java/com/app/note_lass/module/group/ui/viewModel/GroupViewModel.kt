package com.app.note_lass.module.group.ui.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.group.data.CreateGroupState
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupList.GroupListState
import com.app.note_lass.module.group.data.join.EnterGroupState
import com.app.note_lass.module.group.data.join.JoinGroupState
import com.app.note_lass.module.group.domain.repository.CreateGroupUseCase
import com.app.note_lass.module.group.domain.repository.EnterGroupUseCase
import com.app.note_lass.module.group.domain.repository.GetGroupUseCase
import com.app.note_lass.module.group.domain.repository.JoinGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    val createGroupUseCase: CreateGroupUseCase,
    val getGroupUseCase: GetGroupUseCase,
    val enterGroupUseCase: EnterGroupUseCase,
    val joinGroupUseCase: JoinGroupUseCase,
    val dataStore: DataStore<Token>
) : ViewModel() {

    private val _groupCreateState = mutableStateOf(CreateGroupState())
    val groupCreateGroupState = _groupCreateState

    private val _groupListState = mutableStateOf(GroupListState())
    val groupListState = _groupListState

    private val _groupEnterState = mutableStateOf(EnterGroupState())
    val enterGroupState = _groupEnterState

    private var _joinGroupState = mutableStateOf(JoinGroupState())
    val joinGroupState = _joinGroupState

    init {
        getGroupList()
    }

    fun createGroup(
        createRequest: InfoForCreate
    ) {
        createGroupUseCase(createRequest).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _groupCreateState.value = CreateGroupState(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    _groupCreateState.value = CreateGroupState(
                        isLoading = false,
                        isSuccess = true,
                        code = result.data!!
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

    fun enterGroup(code: Int) {
        enterGroupUseCase(code).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _groupEnterState.value = EnterGroupState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _groupEnterState.value = EnterGroupState(
                        isLoading = false,
                        isSuccess = true,
                        groupInfo = result.data!!.groupInfo,
                        groupId = result.data.groupId
                    )
                }

                is Resource.Error -> {
                    if (result.code == 400) {
                        _groupEnterState.value = EnterGroupState(
                            isErrorCode = true,
                            isMessage = "잘못된 코드입니다.",
                            isError = true
                        )
                    } else {
                        _groupEnterState.value = EnterGroupState(
                            isError = true
                        )
                    }
                    Log.e("error in groupEnter", result.code.toString())
                }
            }


        }.launchIn(viewModelScope)
    }


        fun joinGroup(){
            joinGroupUseCase(_groupEnterState.value.groupId).onEach {
                    result ->
                when(result) {
                    is Resource.Loading -> {
                        _joinGroupState.value = JoinGroupState(
                            isLoading = true,
                        )
                    }
                    is Resource.Success  ->{
                        _joinGroupState.value = JoinGroupState(
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                    is Resource.Error -> {

                        if(result.code== 400){
                            _joinGroupState.value =JoinGroupState(
                                isDuplicate = true,
                                isMessage = "이미 등록된 신청입니다",
                                isError = false
                            )
                        }

                       else {
                            _joinGroupState.value = JoinGroupState(
                                isError = true
                            )
                        }
                        Log.e("error in groupJoin",result.code.toString())
                    }
                }


            }.launchIn(viewModelScope)

        }
    fun getGroupList(){
        getGroupUseCase().onEach{
                result ->
            when(result) {
                is Resource.Loading -> {
                    _groupListState.value = GroupListState(
                        isLoading = true,
                        isSuccess = false
                    )
                }
                is Resource.Success  ->{
                    _groupListState.value = GroupListState(
                        isSuccess = true,
                        isError = false,
                        groupList = result.data!!
                    )
                    Log.e("groupListData",result.toString())
                }
                is Resource.Error -> {
                    _groupListState.value = GroupListState(
                        isError = true,
                    )
                }
            }


        }.launchIn(viewModelScope)
    }

}