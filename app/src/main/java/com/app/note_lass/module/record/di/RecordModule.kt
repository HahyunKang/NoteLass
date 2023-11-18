package com.app.note_lass.module.record.di

import com.app.note_lass.common.Constants
import com.app.note_lass.common.OkhttpClient
import com.app.note_lass.module.login.data.LoginApi
import com.app.note_lass.module.login.data.LoginImpl
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.record.data.RecordApi
import com.app.note_lass.module.record.data.RecordImpl
import com.app.note_lass.module.record.domain.RecordRepository
import com.app.note_lass.module.signup.data.SignUpImpl
import com.app.note_lass.module.signup.data.SignupApi
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecordModuleModule {
    @Provides
    @Singleton
    fun recordApi() : RecordApi {
        val client = OkhttpClient().httpClient

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RecordApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecordRepository(api : RecordApi) : RecordRepository {
        return RecordImpl(api)
    }

}