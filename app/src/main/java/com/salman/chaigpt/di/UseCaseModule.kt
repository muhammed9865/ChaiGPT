package com.salman.chaigpt.di

import com.salman.chaigpt.domain.usecase.auth.AutoSignInUseCase
import com.salman.chaigpt.domain.usecase.auth.SignInUseCase
import com.salman.chaigpt.domain.usecase.auth.SignUpUseCase
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/25/2023.
 */
val useCaseModule = module {
    factory {
        AutoSignInUseCase(
            userRepository = get()
        )
    }

    factory {
        SignInUseCase(
            userRepository = get()
        )
    }

    factory {
        SignUpUseCase(
            userRepository = get()
        )
    }
}