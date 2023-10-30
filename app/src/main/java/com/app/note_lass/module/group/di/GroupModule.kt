package com.app.note_lass.module.group.di

import com.app.note_lass.common.Constants
import com.app.note_lass.module.group.data.GroupApi
import com.app.note_lass.module.group.data.GroupImpl
import com.app.note_lass.module.group.domain.repository.GroupRepository
import com.app.note_lass.module.login.data.LoginApi
import com.app.note_lass.module.login.data.LoginImpl
import com.app.note_lass.module.login.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GroupModule {
    @Provides
    @Singleton
    fun groupApi() : GroupApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api : GroupApi) : GroupRepository {
        return GroupImpl(api)
    }

}