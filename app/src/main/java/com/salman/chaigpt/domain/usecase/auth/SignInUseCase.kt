package com.salman.chaigpt.domain.usecase.auth

import com.salman.chaigpt.domain.model.exception.AuthExceptions
import com.salman.chaigpt.domain.repository.UserRepository
import com.salman.chaigpt.domain.usecase.core.ResultUseCase

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
class SignInUseCase(
    private val userRepository: UserRepository,
) : ResultUseCase<SignInUseCase.Params, Unit>() {

    override suspend fun invoke(params: Params): Result<Unit> {
        checkUsernameConstraints(params.username)?.let {
            return Result.failure(it)
        }
        checkPasswordConstraints(params.password)?.let {
            return Result.failure(it)
        }
        return userRepository.signIn(
            params.username,
            params.password,
            params.rememberMe
        )
    }

    private fun checkUsernameConstraints(username: String): Throwable? {
        return when {
            username.isBlank() -> {
                AuthExceptions.EmptyUsernameException
            }
            else -> null
        }
    }

    private fun checkPasswordConstraints(password: String): Throwable? {
        return when {
            password.isBlank() -> {
                AuthExceptions.EmptyPasswordException
            }
            else -> null
        }
    }

    data class Params(
        val username: String,
        val password: String,
        val rememberMe: Boolean,
    ) : ResultUseCase.Params
}