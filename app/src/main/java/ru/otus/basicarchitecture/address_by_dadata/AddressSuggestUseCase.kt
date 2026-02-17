package ru.otus.basicarchitecture.address_by_dadata

import ru.otus.basicarchitecture.UserAddress

class AddressSuggestUseCase(private val addressRepository: AddressCollector) {
    suspend operator fun invoke(query: String): List<UserAddress> {
        return addressRepository.suggestAddress(query)
    }
}