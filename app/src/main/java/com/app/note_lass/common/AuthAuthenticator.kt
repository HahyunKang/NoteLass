package com.app.note_lass.common

import androidx.datastore.core.DataStore
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.data.LoginApi
import com.app.note_lass.module.login.data.LoginImpl
import com.app.note_lass.module.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager  : DataStore<Token>,
) : Authenticator {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })   // LoggingInterceptor 추가
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val loginApi =  Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LoginApi::class.java)
    private val loginRepository = LoginImpl(loginApi)



    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = runBlocking {
            "Bearer ${tokenManager.data.first().accessToken}"
        }
        val refreshToken = runBlocking {
            "refresh_token=${tokenManager.data.first().refreshToken}"
        }


        if(refreshToken.isEmpty()){
            response.close()
            return  null
        }

        val newTokens = try{
             val refreshResponse = runBlocking {
                 loginRepository.refresh(accessToken,refreshToken)
             }
            if(refreshResponse.code ==200){
                refreshResponse.result?.let {
                    auth ->
                    runBlocking {
                        tokenManager.updateData {
                                token ->
                            // preferences.toBuilder().setShowCompleted(completed).build()
                            token.copy(
                                accessToken = auth.token,
                                refreshToken = auth.refreshToken
                            )
                        }
                    }
                }
                refreshResponse
            } else null
        } catch (e : Exception){
            null
        }

        return if (newTokens?.code==200){
            newRequestWithToken(response.request)
        }else null
    }


    private fun newRequestWithToken(request: Request): Request =
        request
            .newBuilder()
            .build()
}