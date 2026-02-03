package ru.otus.basicarchitecture

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.FragmentUserBinding

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding
        get() = _binding ?: throw RuntimeException("FragmentNameBinding == null")

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        addTextChangedListeners()
        binding.buttonNext.setOnClickListener {
            viewModel.validateData()
        }
    }

    fun observeViewModel() {
        viewModel.errName.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    editTextName.error = String.format(
                        resources.getString(R.string.empty_field),
                        textInputName.hint.toString()
                    )
                } else {
                    editTextName.error = null
                }
            }
        }

        viewModel.errSurname.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    editTextSurname.error = String.format(
                        resources.getString(R.string.empty_field),
                        textInputSurname.hint.toString()
                    )
                } else {
                    editTextSurname.error = null
                }
            }
        }

        viewModel.errBirthday.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    editTextBirthday.error = String.format(
                        resources.getString(R.string.empty_field),
                        textInputBirthday.hint.toString()
                    )
                } else {
                    editTextBirthday.error = null
                }
            }
        }

        viewModel.errAge.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    editTextBirthday.error = resources.getString(R.string.invalid_age)
                } else {
                    editTextBirthday.error = null
                }
            }
        }

        viewModel.errBirthdayFormat.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    editTextBirthday.error = resources.getString(R.string.invalid_birthday)
                } else {
                    editTextBirthday.error = null
                }
            }
        }

        viewModel.canGoNext.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, AddressFragment())
                    .commit()
            }
        }
    }

    private fun addTextChangedListeners() {

        with(binding) {
            editTextName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    viewModel.setName(editTextName.text.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            editTextSurname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    viewModel.setSurname(editTextSurname.text.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            editTextBirthday.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    viewModel.setBirthday(editTextBirthday.text.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}