package ru.otus.basicarchitecture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import ru.otus.basicarchitecture.databinding.FragmentResultBinding
import kotlin.getValue
import android.content.res.ColorStateList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding == null")

    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.loadUserInfo()
    }

    private fun observeViewModel(){
        viewModel.firstName.observe(viewLifecycleOwner) {
            with(binding){
                textViewName.text = it
            }
        }

        viewModel.lastName.observe(viewLifecycleOwner) {
            with(binding){
                textViewSurname.text = it
            }
        }

        viewModel.birthDate.observe(viewLifecycleOwner) {
            with(binding){
                textViewBirthday.text = it
            }
        }

        viewModel.country.observe(viewLifecycleOwner) {
            with(binding){
                textViewCountry.text = it
            }
        }

        viewModel.city.observe(viewLifecycleOwner) {
            with(binding){
                textViewCity.text = it
            }
        }

        viewModel.address.observe(viewLifecycleOwner) {
            with(binding){
                textViewAddress.text = it
            }
        }

        viewModel.interests.observe(viewLifecycleOwner) {
            val defaultColor = ContextCompat.getColor(requireContext(), R.color.gray_light)
            with(binding){
                it.forEach { interest ->
                    val chip = Chip(requireContext()).apply {
                        text = interest
                        chipBackgroundColor = ColorStateList.valueOf(defaultColor)
                    }
                    binding.chipGroupInterests.addView(chip)
                }
            }
        }
    }
}