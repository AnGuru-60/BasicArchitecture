package ru.otus.basicarchitecture.address_by_dadata

data class AddressSuggestionDto(
    val value: String,
    val unrestricted_value: String,
    val data: AddressDataDto
)