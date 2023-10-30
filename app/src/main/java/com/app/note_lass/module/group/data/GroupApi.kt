package com.app.note_lass.module.group.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.groupList.GroupListDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GroupApi {

    @POST("api/group")
    suspend fun createGroup(
        @Header(value = "Authorization") accessToken : String,
        @Body groupCreateRequest : InfoForCreate
    ) : NoteResponseBody<Int>

    @GET("api/group")
    suspend fun getGroupList(
        @Header(value = "Authorization") accessToken : String,
    ) : NoteResponseBody<GroupListDto>
}