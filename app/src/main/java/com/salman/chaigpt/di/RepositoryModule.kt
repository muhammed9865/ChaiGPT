package com.salman.chaigpt.di

import com.salman.chaigpt.data.repository.ChatRepositoryImpl
import com.salman.chaigpt.data.repository.UserRepositoryImpl
import com.salman.chaigpt.domain.repository.ChatRepository
import com.salman.chaigpt.domain.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 * Contains Repositories and UseCases providers
 */
val repositoryModule = module {
    // ChatRepository Single
    singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }

    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}