package ru.otus.basicarchitecture.address_by_dadata

import ru.otus.basicarchitecture.address_by_dadata.AddressSuggestionDto

data class AddressResponseDto(
    val suggestions: List<AddressSuggestionDto>
)