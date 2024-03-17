package com.app.note_lass.module.dashboard.di

import androidx.datastore.core.DataStore
import com.app.note_lass.common.AuthAuthenticator
import com.app.note_lass.common.Constants
import com.app.note_lass.common.OkhttpClient
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.dashboard.data.DashboardApi
import com.app.note_lass.module.dashboard.data.DashboardImpl
import com.app.note_lass.module.dashboard.domain.DashboardRepository
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {


    @Provides
    @Singleton
    fun dashboardApi(client: OkHttpClient) : DashboardApi {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DashboardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api : DashboardApi) : DashboardRepository {
        return DashboardImpl(api)
    }

}