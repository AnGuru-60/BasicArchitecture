package ru.otus.basicarchitecture.address_by_dadata

import android.util.Log
import ru.otus.basicarchitecture.BuildConfig
import ru.otus.basicarchitecture.UserAddress
import javax.inject.Inject
import java.io.IOException

class AddressCollectorImpl @Inject constructor(
    private val addressApiService: AddressApiService
) : AddressCollector {
    override suspend fun suggestAddress(query: String): List<UserAddress> {
        val token = "Token ${BuildConfig.dadata_api_key}"

        val response = addressApiService.suggestAddress(token, AddressRequestDto(query))

        if (!response.isSuccessful){
            val errorResponse = response.errorBody()?.string()
            Log.e("API Error", "Error response: $errorResponse")
            throw IOException("Error response: $errorResponse")
        }

        val listAddressDataDto = response.body()?.suggestions?.map { suggestion ->
            suggestion.data.copy(fullAddress = suggestion.unrestricted_value)
        }

        val mapper = AddressMapper()
        val listUserAddress = listAddressDataDto?.map {
            mapper.mapDtoToEntity(it)
        }

        return listUserAddress ?: listOf()
    }
}