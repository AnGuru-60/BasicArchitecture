package ru.otus.basicarchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.address_by_dadata.AddressSuggestUseCase
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val cache: WizardCache,
    private val addressSuggestUseCase: AddressSuggestUseCase
): ViewModel() {
    private var _listUserAddress = MutableLiveData<List<UserAddress>>()
    val listUserAddress: LiveData<List<UserAddress>>
        get() = _listUserAddress
    private var _canGoNext = MutableLiveData<Boolean>(false)
    val canGoNext: LiveData<Boolean> get() = _canGoNext
    private var _errAddress = MutableLiveData<Boolean>()
    val errAddress: LiveData<Boolean> get() = _errAddress
    private var _errorNetwork = MutableLiveData<Boolean>()
    val errorNetwork: LiveData<Boolean>
        get() = _errorNetwork

    fun validateData() {
        var success = checkEmptyFields()

        if (success == false){
            _canGoNext.value = false
            return
        }
        _canGoNext.value = true
    }

    fun setAddress(fullAddress: String) {
        cache.userAddress.fullAddress = fullAddress
    }

    fun searchAddress(query: String) {
        viewModelScope.launch {
            try {
                val result = addressSuggestUseCase.invoke(query)
                _listUserAddress.postValue(result)
            } catch (e: Exception) {
                _errorNetwork.postValue(true)
            }
        }
    }

    private fun checkEmptyFields(): Boolean{
        var success = true

        if (cache.userAddress.fullAddress.isBlank()){
            _errAddress.value = true
            success = false
        } else{
            _errAddress.value = false
        }
        return success
    }
}