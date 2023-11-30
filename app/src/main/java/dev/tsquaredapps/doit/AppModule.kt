package dev.tsquaredapps.doit

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.tsquaredapps.doit.data.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "do-db")
            .build()
}