package ru.otus.basicarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.otus.basicarchitecture.address_by_dadata.AddressSuggestUseCase

class AddressViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val wizardCache: WizardCache = mock()
    private val addressSuggestUseCase: AddressSuggestUseCase = mock()
    private val viewModel: AddressViewModel = AddressViewModel(wizardCache, addressSuggestUseCase)

    @Before
    fun before(){
        Dispatchers.setMain(testDispatcher)
        println("Старт теста")
    }
    @After
    fun after(){
        Dispatchers.resetMain()
        println("Завершение теста")
    }
    @Test
    fun `Empty full address error`() {
        runTest {
            whenever(wizardCache.userAddress).thenReturn(UserAddress("", "", "", "", "", ""))
            viewModel.validateData()
            val actual = viewModel.errAddress.value ?: throw RuntimeException("errAddress == null")
            assertTrue(actual)
        }
    }
    @Test
    fun `Valid input full address`() {
        runTest {
            whenever(wizardCache.userAddress).thenReturn(UserAddress("", "", "", "", "", "Россия, Москва"))
            viewModel.validateData()
            val actual = viewModel.errAddress.value ?: throw RuntimeException("errAddress == null")
            assertFalse(actual)
        }
    }
    @Test
    fun `Network success`() {
        runTest {
            whenever(addressSuggestUseCase.invoke(any())).thenReturn(getAddress())
            launch {
                viewModel.searchAddress("query")
            }
            advanceUntilIdle()
            val actual = viewModel.listUserAddress.value
            assertNotNull(actual)
        }
    }

    @Test
    fun `Network error`() {
        runTest {
            whenever(addressSuggestUseCase.invoke(any())).thenThrow(RuntimeException("Network error"))
            launch {
                viewModel.searchAddress("query")
            }
            advanceUntilIdle()
            val actual = viewModel.errorNetwork.value ?: throw RuntimeException("errorNetwork == null")
            assertTrue(actual)
        }
    }

    private fun getAddress(): List<UserAddress> {
        return listOf(UserAddress("Россия", "Москва", "Ленинский", "10", "", "Россия, Москва, Ленинский, 10"))
    }
}