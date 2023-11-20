package com.app.note_lass.module.student.di

import com.app.note_lass.common.Constants
import com.app.note_lass.common.OkhttpClient
import com.app.note_lass.module.login.data.LoginApi
import com.app.note_lass.module.login.data.LoginImpl
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.login.domain.repository.StudentRepository
import com.app.note_lass.module.signup.data.SignUpImpl
import com.app.note_lass.module.signup.data.SignupApi
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import com.app.note_lass.module.student.data.StudentApi
import com.app.note_lass.module.student.data.StudentImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudentModule {
    val client = OkhttpClient().httpClient
    @Provides
    @Singleton
    fun studentApi() : StudentApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(StudentApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideStudentRepository(api : StudentApi) : StudentRepository {
        return StudentImpl(api)
    }

}