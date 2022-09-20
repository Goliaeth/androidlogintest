package com.goliaeth.logintestapp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.goliaeth.logintestapp.R
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.databinding.FragmentLoginBinding
import com.goliaeth.logintestapp.ui.enable
import com.goliaeth.logintestapp.ui.handleApiError
import com.goliaeth.logintestapp.ui.home.HomeActivity
import com.goliaeth.logintestapp.ui.startNewActivity
import com.goliaeth.logintestapp.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.progressbar.visible(false)
        binding.loginButton.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.value.access_token!!,
                            it.value.refresh_token!!
                        )
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
                is Resource.Loading -> {
                    binding.loginButton.enable(true)
                }
            }
        }

        binding.passwordTextEdit.addTextChangedListener {
            val email = binding.loginTextEdit.text.toString().trim()
            binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.loginTextEdit.text.toString().trim()
        val password = binding.passwordTextEdit.text.toString().trim()
        viewModel.login(email, password)
    }

}