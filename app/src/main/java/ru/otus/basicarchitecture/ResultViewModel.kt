package ru.otus.basicarchitecture

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val cache: WizardCache): ViewModel() {
    private var _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _firstName
    private var _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> get() = _lastName
    private var _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String> get() = _birthDate
    private var _country = MutableLiveData<String>()
    val country: LiveData<String> get() = _country
    private var _city = MutableLiveData<String>()
    val city: LiveData<String> get() = _city
    private var _address = MutableLiveData<String>()
    val address: LiveData<String> get() = _address
    private var _interests = MutableLiveData<List<String>>()
    val interests: LiveData<List<String>> get() = _interests

    fun loadUserInfo(){
        _firstName.value = cache.firstName
        _lastName.value = cache.lastName
        _birthDate.value = cache.birthDate
        _country.value = cache.userAddress.country
        _city.value = cache.userAddress.city
        _address.value = cache.userAddress.fullAddress
        _interests.value = cache.interests
    }
}