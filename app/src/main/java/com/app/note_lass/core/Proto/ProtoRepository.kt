package com.app.note_lass.core.Proto

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProtoRepository @Inject constructor(
    val protoDataStore: DataStore<Token>
) {
    val tokens: Flow<Token> = protoDataStore.data

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

}