package ru.otus.basicarchitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class UserViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val wizardCache: WizardCache = mock()
    private val viewModel = UserViewModel(wizardCache)
    @Before
    fun before(){
        println("Старт теста")
    }
    @After
    fun after(){
        println("Завершение теста")
    }
    @Test
    fun `Empty firstName error`() {
        runTest {
            whenever(wizardCache.firstName).thenReturn("")
            whenever(wizardCache.lastName).thenReturn("Владимиров")
            whenever(wizardCache.birthDate).thenReturn("10.10.2000")
            viewModel.validateData()
            val actual = viewModel.errName.value ?: throw RuntimeException("errName == null")
            assertTrue(actual)
        }
    }
    @Test
    fun `Empty lastName error`() {
        runTest {
            whenever(wizardCache.firstName).thenReturn("Владимир")
            whenever(wizardCache.lastName).thenReturn("")
            whenever(wizardCache.birthDate).thenReturn("10.10.2000")
            viewModel.validateData()
            val actual = viewModel.errSurname.value ?: throw RuntimeException("errSurname == null")
            assertTrue(actual)
        }
    }
    @Test
    fun `Empty birthday error`() {
        runTest {
            whenever(wizardCache.firstName).thenReturn("Владимир")
            whenever(wizardCache.lastName).thenReturn("Владимиров")
            whenever(wizardCache.birthDate).thenReturn("")
            viewModel.validateData()
            val actual = viewModel.errBirthday.value ?: throw RuntimeException("errBirthday == null")
            assertTrue(actual)
        }
    }
    @Test
    fun `Legal age error`() {
        runTest {
            whenever(wizardCache.firstName).thenReturn("Владимир")
            whenever(wizardCache.lastName).thenReturn("Владимиров")
            whenever(wizardCache.birthDate).thenReturn("01.01.2010")
            viewModel.validateData()
            val actual = viewModel.errAge.value ?: throw RuntimeException("errAge == null")
            assertTrue(actual)
        }
    }
    @Test
    fun `Birthday format error`() {
        runTest {
            whenever(wizardCache.firstName).thenReturn("Владимир")
            whenever(wizardCache.lastName).thenReturn("Владимиров")
            whenever(wizardCache.birthDate).thenReturn("01012010")
            viewModel.validateData()
            val actual = viewModel.errBirthdayFormat.value ?: throw RuntimeException("errBirthdayFormat == null")
            assertTrue(actual)
        }
    }
    @Test
    fun `Valid input user data`() {
        runTest {
            whenever(wizardCache.firstName).thenReturn("Владимир")
            whenever(wizardCache.lastName).thenReturn("Владимиров")
            whenever(wizardCache.birthDate).thenReturn("10.10.2000")
            viewModel.validateData()
            val actualList = listOf<Boolean?>(
                viewModel.errName.value,
                viewModel.errSurname.value,
                viewModel.errBirthday.value,
                viewModel.errAge.value,
                viewModel.errBirthdayFormat.value,
            )
            if (actualList.contains(null)) {
                throw RuntimeException("actual == null")
            }
            val actual = actualList.contains(true)
            assertFalse(actual)
        }
    }
}