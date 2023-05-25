package com.salman.chaigpt.domain.usecase.auth

import com.salman.chaigpt.domain.model.exception.AuthExceptions
import com.salman.chaigpt.domain.repository.UserRepository
import com.salman.chaigpt.domain.usecase.core.ResultUseCase

/**
* Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
*/
class SignUpUseCase(
    private val userRepository: UserRepository
): ResultUseCase<SignUpUseCase.Params, Unit>() {

    companion object {
        private const val MIN_USERNAME_LENGTH = 3
        private const val MIN_PASSWORD_LENGTH = 8
    }
    override suspend fun invoke(params: Params): Result<Unit> {
        checkUsernameConstraints(params.username)?.let {
            return Result.failure(it)
        }
        checkEmailConstraints(params.email)?.let {
            return Result.failure(it)
        }
        checkPasswordConstraints(params.password)?.let {
            return Result.failure(it)
        }
        return userRepository.signUp(params.username, params.email, params.password)
    }

    private fun checkUsernameConstraints(username: String): Throwable? {
        return when {
            username.isBlank() -> {
                AuthExceptions.EmptyUsernameException
            }
            username.length < MIN_USERNAME_LENGTH -> {
                AuthExceptions.ShortUsernameException
            }
            username.matches(Regex(".*[^A-Za-z0-9].*")) -> {
                AuthExceptions.InvalidUsernameException
            }
            else -> null
        }
    }

    private fun checkEmailConstraints(email: String): Throwable? {
        return when {
            email.isBlank() -> {
                AuthExceptions.EmptyEmailException
            }
            email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) -> {
                AuthExceptions.InvalidEmailException
            }
            else -> null
        }
    }

    private fun checkPasswordConstraints(password: String): Throwable? {
        return when {
            password.isBlank() -> {
                AuthExceptions.EmptyPasswordException
            }
            password.length < MIN_PASSWORD_LENGTH -> {
                AuthExceptions.ShortPasswordException
            }
            password.matches(Regex(".*[^A-Za-z0-9].*")) -> {
                AuthExceptions.InvalidPasswordException
            }
            else -> null
        }
    }

    data class Params(
        val username: String,
        val email: String,
        val password: String
    ): ResultUseCase.Params
}