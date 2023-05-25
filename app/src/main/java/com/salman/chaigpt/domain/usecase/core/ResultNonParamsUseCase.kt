package com.salman.chaigpt.domain.usecase.core

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/25/2023.
 */
abstract class ResultNonParamsUseCase<Out> {

    abstract suspend operator fun invoke(): Result<Out>

}