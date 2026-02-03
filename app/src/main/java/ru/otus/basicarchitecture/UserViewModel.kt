package ru.otus.basicarchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val cache: WizardCache
): ViewModel() {
    private var _canGoNext = MutableLiveData<Boolean>(false)
    val canGoNext: LiveData<Boolean> get() = _canGoNext
    private var _errAge = MutableLiveData<Boolean>()
    val errAge: LiveData<Boolean> get() = _errAge
    private var _errName = MutableLiveData<Boolean>()
    val errName: LiveData<Boolean> get() = _errName
    private var _errSurname = MutableLiveData<Boolean>()
    val errSurname: LiveData<Boolean> get() = _errSurname
    private var _errBirthday = MutableLiveData<Boolean>()
    val errBirthday: LiveData<Boolean> get() = _errBirthday
    private var _errBirthdayFormat = MutableLiveData<Boolean>()
    val errBirthdayFormat: LiveData<Boolean> get() = _errBirthdayFormat

    fun validateData() {
        var success = checkEmptyFields()

        if (success == false){
            _canGoNext.value = false
            return
        }

        success = checkAge()

        if (success == false){
            _canGoNext.value = false
            return
        }
        _canGoNext.value = true
    }

    fun setName(name: String) {
        cache.firstName = name
    }

    fun setSurname(surname: String) {
        cache.lastName = surname
    }

    fun setBirthday(birthday: String) {
        cache.birthDate = birthday
    }

    private fun checkAge(): Boolean {
        var success = true
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        try {
            val birthDate = sdf.parse(cache.birthDate)
            _errBirthdayFormat.value = false

            val today = Calendar.getInstance().time
            val diff = today.time - birthDate.time
            val years = (diff / (1000L * 60 * 60 *24 * 365)).toInt()

            if (years < 18) {
                _errAge.value = true
                success = false
            } else {
                _errAge.value = false
            }
        } catch (e: Exception) {
            _errBirthdayFormat.value = true
            success = false
        }

        return success
    }

    private fun checkEmptyFields(): Boolean{
        var success = true

        if (cache.firstName == ""){
            _errName.value = true
            success = false
        } else{
            _errName.value = false
        }

        if (cache.lastName == ""){
            _errSurname.value = true
            success = false
        } else {
            _errSurname.value = false
        }

        if (cache.birthDate == ""){
            _errBirthday.value = true
            success = false
        } else{
            _errBirthday.value = false
        }
        return success
    }
}
