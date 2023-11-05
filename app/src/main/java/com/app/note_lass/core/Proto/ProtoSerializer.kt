package com.app.note_lass.core.Proto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.app.note_lass.common.Constants.DATA_STORE_FILE_NAME
import com.app.note_lass.common.Constants.DATA_STORE_GROUP_FILE_NAME
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ProtoSerializer : Serializer<Token> {
    override val defaultValue: Token
        get() = Token(
            accessToken = null,
            role = Role.NONE
        )

    override suspend fun readFrom(input: InputStream): Token {
      return try {
        Json.decodeFromString(
            deserializer = Token.serializer() ,
            string = input.readBytes().decodeToString()
        )
    } catch (e: SerializationException) {
        e.printStackTrace()
        defaultValue
    }
    }

    override suspend fun writeTo(t: Token, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Token.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

    val Context.tokenDataStore: DataStore<Token> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = ProtoSerializer
    )
}
object ProtoSerializer_Group : Serializer<GroupInfo> {
    override val defaultValue: GroupInfo
        get() =GroupInfo(
            groupName = null,
            teacherName = null,
            groupId = null
        )

    override suspend fun readFrom(input: InputStream): GroupInfo {
        return try {
            Json.decodeFromString(
                deserializer = GroupInfo.serializer() ,
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: GroupInfo, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = GroupInfo.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

    val Context.tokenDataStore: DataStore<GroupInfo> by dataStore(
        fileName = DATA_STORE_GROUP_FILE_NAME,
        serializer = ProtoSerializer_Group
    )
}