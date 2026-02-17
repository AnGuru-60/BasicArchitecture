package ru.otus.basicarchitecture.address_by_dadata

import ru.otus.basicarchitecture.UserAddress

class AddressMapper {
    fun mapDtoToEntity(dto: AddressDataDto) = UserAddress(
        fullAddress = dto.fullAddress ?: "",
        country = dto.country ?: "",
        city = dto.city ?: "",
        street = dto.street ?: "",
        house = dto.house ?: "",
        block = dto.block ?: ""
    )
}