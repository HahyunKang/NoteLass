package com.app.note_lass.module.note.di

import androidx.datastore.core.DataStore
import com.app.note_lass.common.AuthAuthenticator
import com.app.note_lass.common.Constants
import com.app.note_lass.common.OkhttpClient
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.data.LoginApi
import com.app.note_lass.module.login.data.LoginImpl
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.note.data.NoteApi
import com.app.note_lass.module.note.data.NoteImpl
import com.app.note_lass.module.note.domain.NoteRepository
import com.app.note_lass.module.signup.data.SignUpImpl
import com.app.note_lass.module.signup.data.SignupApi
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import com.app.note_lass.module.student.di.StudentModule
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
object NoteModule {


    @Provides
    @Singleton
    fun noteApi(client: OkHttpClient) : NoteApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(api : NoteApi) : NoteRepository {
        return NoteImpl(api)
    }

}