package ru.otus.basicarchitecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.otus.basicarchitecture.address_by_dadata.AddressApiService
import ru.otus.basicarchitecture.address_by_dadata.AddressCollector
import ru.otus.basicarchitecture.address_by_dadata.AddressCollectorImpl
import ru.otus.basicarchitecture.address_by_dadata.AddressSuggestUseCase
import javax.inject.Singleton

@HiltAndroidApp
class HiltApplication : Application() {

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://suggestions.dadata.ru/suggestions/api/4_1/rs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDaDataService(retrofit: Retrofit): AddressApiService {
        return retrofit.create(AddressApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAddressRepository(impl: AddressCollectorImpl): AddressCollector {
        return impl
    }

    @Provides
    @Singleton
    fun provideAddressSuggestUseCase(repository: AddressCollector): AddressSuggestUseCase {
        return AddressSuggestUseCase(repository)
    }
}