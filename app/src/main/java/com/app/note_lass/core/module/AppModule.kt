package com.app.note_lass.core.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.app.note_lass.common.Constants
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoSerializer
import com.app.note_lass.core.Proto.ProtoSerializer_Group
import com.app.note_lass.core.Proto.Token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(
        @ApplicationContext applicationContext: Context
    ): DataStore<Token> {
        return DataStoreFactory.create(
            serializer = ProtoSerializer,
            produceFile = { applicationContext.dataStoreFile(Constants.DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
        )
    }

    @Singleton
    @Provides
    fun provideProtoGroupDataStore(
        @ApplicationContext applicationContext: Context
    ): DataStore<GroupInfo> {
        return DataStoreFactory.create(
            serializer = ProtoSerializer_Group,
            produceFile = { applicationContext.dataStoreFile(Constants.DATA_STORE_GROUP_FILE_NAME) },
            corruptionHandler = null,
        )
    }
}