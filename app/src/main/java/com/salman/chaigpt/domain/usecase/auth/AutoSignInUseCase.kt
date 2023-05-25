package com.salman.chaigpt.domain.usecase.auth

import com.salman.chaigpt.domain.repository.UserRepository
import com.salman.chaigpt.domain.usecase.core.ResultNonParamsUseCase

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/25/2023.
 */
class AutoSignInUseCase(
    private val userRepository: UserRepository
): ResultNonParamsUseCase<Unit>() {
    override suspend fun invoke(): Result<Unit> {
        return userRepository.signInAutomatically()
    }
}