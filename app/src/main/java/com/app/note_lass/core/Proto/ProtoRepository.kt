package com.app.note_lass.core.Proto

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProtoRepository @Inject constructor(
    val protoDataStore: DataStore<Token>,
    val protoGroupDataStore: DataStore<GroupInfo>
) {
    val tokens: Flow<Token> = protoDataStore.data

    val groupInfo : Flow<GroupInfo> = protoGroupDataStore.data

    suspend fun updateToken(accessToken : String,refreshToken : String) {
        protoDataStore.updateData { token ->
            // preferences.toBuilder().setShowCompleted(completed).build()
            token.copy(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
    suspend fun updateRefreshToken(refreshToken: String) {
        protoDataStore.updateData { token ->
            // preferences.toBuilder().setShowCompleted(completed).build()
            token.copy(
                refreshToken = refreshToken
            )
        }
    }


    suspend fun updateAccessToken(accessToken : String) {
        protoDataStore.updateData { token ->
            // preferences.toBuilder().setShowCompleted(completed).build()
            token.copy(
                accessToken = accessToken
            )
        }
    }

    suspend fun updateRole(role : Role){
        protoDataStore.updateData { token ->
            token.copy(
                role = role
            )
        }
    }
    suspend fun resetToken() {
        protoDataStore.updateData { token ->
            token.copy(
                accessToken = null,
                role = Role.NONE
            )
        }
    }

    suspend fun updateGroupInfo(groupInfo: GroupInfo) {
        protoGroupDataStore.updateData { info ->
            // preferences.toBuilder().setShowCompleted(completed).build()
            info.copy(
                groupName = groupInfo.groupName,
                teacherName = groupInfo.teacherName,
                groupId = groupInfo.groupId
            )
        }
    }

    suspend fun resetGroupInfo() {
        protoGroupDataStore.updateData { groupInfo->
            groupInfo.copy(
               groupName = null,
                teacherName = null,
                groupId = null
            )
        }
    }

}