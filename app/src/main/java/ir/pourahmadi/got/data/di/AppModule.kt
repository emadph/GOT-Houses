package ir.pourahmadi.got.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pourahmadi.got.data.di.common.NetworkModule
import ir.pourahmadi.got.data.local.Database
import ir.pourahmadi.got.data.remote.api.AppApi
import ir.pourahmadi.got.data.repository.AppRepositoryImpl
import ir.pourahmadi.got.domain.common.error.ErrorHandler
import ir.pourahmadi.got.domain.repository.AppRepository
import ir.pourahmadi.got.domain.use_case.AppUseCase
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUseCase(
        @ApplicationContext context: Context,
        repository: AppRepository
    ): AppUseCase {
        return AppUseCase(context, repository)
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: AppApi,
        errorHandler: ErrorHandler,
        db: Database,
    ): AppRepository {
        return AppRepositoryImpl(api, errorHandler, db.Dao)
    }
}