package com.goliaeth.logintestapp.ui.auth

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.goliaeth.logintestapp.data.network.AuthAPI
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.repository.AuthRepository
import com.goliaeth.logintestapp.databinding.FragmentLoginBinding
import com.goliaeth.logintestapp.ui.base.BaseFragment
import com.goliaeth.logintestapp.ui.enable
import com.goliaeth.logintestapp.ui.home.HomeActivity
import com.goliaeth.logintestapp.ui.startNewActivity
import com.goliaeth.logintestapp.ui.visible

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(ActivityLifeCycleObserver {
            binding.progressbar.visible(false)
            binding.loginButton.enable(false)

            viewModel.loginResponse.observe(viewLifecycleOwner) {
                binding.progressbar.visible(false)
                when (it) {
                    is Resource.Success -> {
                        viewModel.saveAuthToken(it.value.token)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "Login failure", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        TODO("fffff")
                    }
                }
            }

            binding.passwordTextEdit.addTextChangedListener {
                val email = binding.loginTextEdit.text.toString().trim()
                binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
            }

            binding.loginButton.setOnClickListener {
                val email = binding.loginTextEdit.text.toString().trim()
                val password = binding.passwordTextEdit.text.toString().trim()
                binding.progressbar.visible(true)
                viewModel.login(email, password)
            }
        })
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildAPI(AuthAPI::class.java), userPreferences)

}