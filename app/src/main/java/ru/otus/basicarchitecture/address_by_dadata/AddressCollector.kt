package ru.otus.basicarchitecture.address_by_dadata

import ru.otus.basicarchitecture.UserAddress

interface AddressCollector {
    suspend fun suggestAddress(query: String): List<UserAddress>
}