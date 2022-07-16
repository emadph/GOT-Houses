package ir.pourahmadi.got.domain.common.error

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}