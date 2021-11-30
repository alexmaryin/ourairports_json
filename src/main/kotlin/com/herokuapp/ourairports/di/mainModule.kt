package com.herokuapp.ourairports.di

import com.herokuapp.ourairports.repository.RunwaysRepository
import com.herokuapp.ourairports.repository.RunwaysRepositoryImpl
import org.koin.dsl.module

val mainModule = module {
    single<RunwaysRepository> { RunwaysRepositoryImpl() }
}