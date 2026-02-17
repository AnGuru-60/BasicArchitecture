package ru.otus.basicarchitecture.address_by_dadata

data class AddressDataDto(
    val country: String?,
    val city: String?,
    val street: String?,
    val house: String?,
    val block: String?,
    val fullAddress: String? = null
)