package com.app.note_lass.module.signup.di

import com.app.note_lass.common.Constants
import com.app.note_lass.common.OkhttpClient
import com.app.note_lass.module.signup.data.SignUpImpl
import com.app.note_lass.module.signup.data.SignupApi
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    val client = OkhttpClient().httpClient

    @Provides
    @Singleton
    fun provideSignUpApi() :SignupApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(SignupApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSignUpRepository(api : SignupApi) : SignUpRepository{
        return SignUpImpl(api)
    }
}