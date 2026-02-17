package ru.otus.basicarchitecture

import javax.inject.Inject
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    var firstName: String = ""
    var lastName: String = ""
    var birthDate: String = ""
    var userAddress: UserAddress = UserAddress("", "", "", "", "", "")
    var interests: List<String> = emptyList()
}

data class UserAddress(
    var country: String,
    var city: String,
    var street: String,
    var house: String,
    var block: String,
    var fullAddress: String
)
