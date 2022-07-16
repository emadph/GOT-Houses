package ir.pourahmadi.got.utils

import ir.pourahmadi.got.domain.common.base.BaseResult
import ir.pourahmadi.got.domain.common.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <T> safeCall(
    errorHandler: ErrorHandler,
    before: (suspend () -> BaseResult<T>)? = null,
    action: suspend () -> BaseResult<T>
): Flow<BaseResult<T>> {
    return flow {
        try {
            if (before != null) {
                emit(before())
            }
            emit(action())
        } catch (throwable: Throwable) {
            emit(
                BaseResult.NetworkError(errorHandler.getError(throwable))
            )
        }
    }
}



