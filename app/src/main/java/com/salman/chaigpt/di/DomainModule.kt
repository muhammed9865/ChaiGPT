package com.salman.chaigpt.di

import com.salman.chaigpt.data.repository.ChatRepositoryImpl
import com.salman.chaigpt.domain.repository.ChatRepository
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/22/2023.
 * Contains Repositories and UseCases providers
 */
val domainModule = module {
    // ChatRepository Single
    single<ChatRepository> {
        ChatRepositoryImpl(
            dataSource = get()
        )
    }
}