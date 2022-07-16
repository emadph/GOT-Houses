package ir.pourahmadi.got.data.di.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pourahmadi.got.data.common.GeneralErrorHandlerImpl
import ir.pourahmadi.got.domain.common.error.ErrorHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ErrorModule {

    @Singleton
    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return GeneralErrorHandlerImpl()
    }


}