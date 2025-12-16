package com.pruebas.airolmagic

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import com.pruebas.airolmagic.data.database.CharacterRepository
import com.pruebas.airolmagic.data.database.ChatRepository
import com.pruebas.airolmagic.data.database.DataSources
import com.pruebas.airolmagic.data.database.GameRepository
import com.pruebas.airolmagic.data.database.GeneralRepository
import com.pruebas.airolmagic.data.database.ItemsRepository
import com.pruebas.airolmagic.data.database.SpellsCantripsRepository
import com.pruebas.airolmagic.data.database.WatchersRepository
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataSources(): DataSources { return DataSources() }

    @Provides
    @Singleton
    fun provideGameRepository(): GameRepository { return GameRepository() }

    @Provides
    @Singleton
    fun provideGeneralRepository(): GeneralRepository { return GeneralRepository() }

    @Provides
    @Singleton
    fun provideWatchersRepository(): WatchersRepository { return WatchersRepository() }

    @Provides
    @Singleton
    fun provideCharacterRepository(): CharacterRepository { return CharacterRepository() }

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository { return ChatRepository() }

    @Provides
    @Singleton
    fun provideSpellsCantripsRepository(dataSources: DataSources): SpellsCantripsRepository {
        return SpellsCantripsRepository(dataSources)
    }

    @Provides
    @Singleton
    fun provideItemsRepository(dataSources: DataSources): ItemsRepository {
        return ItemsRepository(dataSources)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}