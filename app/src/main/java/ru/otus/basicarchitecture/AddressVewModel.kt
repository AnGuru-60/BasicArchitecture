package ru.otus.basicarchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val cache: WizardCache
): ViewModel() {
    private var _canGoNext = MutableLiveData<Boolean>(false)
    val canGoNext: LiveData<Boolean> get() = _canGoNext
    private var _errCity = MutableLiveData<Boolean>()
    val errCity: LiveData<Boolean> get() = _errCity
    private var _errCountry = MutableLiveData<Boolean>()
    val errCountry: LiveData<Boolean> get() = _errCountry
    private var _errAddress = MutableLiveData<Boolean>()
    val errAddress: LiveData<Boolean> get() = _errAddress

    fun validateData() {
        var success = checkEmptyFields()

        if (success == false){
            _canGoNext.value = false
            return
        }
        _canGoNext.value = true
    }

    fun setCountry(country: String) {
        cache.country = country
    }

    fun setCity(city: String) {
        cache.city = city
    }

    fun setAddress(address: String) {
        cache.address = address
    }

    private fun checkEmptyFields(): Boolean{
        var success = true

        if (cache.country == ""){
            _errCountry.value = true
            success = false
        } else{
            _errCountry.value = false
        }

        if (cache.city == ""){
            _errCity.value = true
            success = false
        } else {
            _errCity.value = false
        }

        if (cache.address == ""){
            _errAddress.value = true
            success = false
        } else{
            _errAddress.value = false
        }
        return success
    }
}