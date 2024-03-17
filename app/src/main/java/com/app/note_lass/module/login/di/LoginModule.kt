package com.app.note_lass.module.login.di

import androidx.datastore.core.DataStore
import com.app.note_lass.common.AuthAuthenticator
import com.app.note_lass.common.Constants
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.data.LoginApi
import com.app.note_lass.module.login.data.LoginImpl
import com.app.note_lass.module.login.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authAuthenticator: AuthAuthenticator): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })   // LoggingInterceptor 추가
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .authenticator(authAuthenticator) // 여기서 AuthAuthenticator를 사용하여 인증 실패 시 처리를 담당합니다.
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthAuthenticator(
        tokenManager: DataStore<Token>,
    ): AuthAuthenticator {
        // AuthAuthenticator 인스턴스를 생성하여 반환합니다.
        return AuthAuthenticator(tokenManager)
    }

    @Provides
    @Singleton
    fun loginApi(client: OkHttpClient): LoginApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }


    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return LoginImpl(api)
    }

}