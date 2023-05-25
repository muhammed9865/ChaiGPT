package com.salman.chaigpt.domain.model.exception

/**
* Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
*/
sealed class AuthExceptions: Exception() {
    object EmptyUsernameException : AuthExceptions()
    object EmptyEmailException : AuthExceptions()
    object EmptyPasswordException : AuthExceptions()

    object ShortUsernameException : AuthExceptions()
    object ShortPasswordException : AuthExceptions()
    object InvalidEmailException : AuthExceptions()
    object InvalidPasswordException : AuthExceptions()
    object InvalidUsernameException : AuthExceptions()
}
