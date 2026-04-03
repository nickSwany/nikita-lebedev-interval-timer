package com.example.nikita_lebedev_interval_timer.di.module

import com.example.nikita_lebedev_interval_timer.data.impl.TimerRepositoryImpl
import com.example.nikita_lebedev_interval_timer.domain.repository.TimerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTimerRepository(timerRepositoryImpl: TimerRepositoryImpl): TimerRepository

}