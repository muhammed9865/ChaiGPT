package com.salman.chaigpt.domain.usecase.core

/**
* Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
*/
abstract class ResultUseCase <In: ResultUseCase.Params, Out> {
    abstract suspend operator fun invoke(params: In): Result<Out>

    interface Params
}