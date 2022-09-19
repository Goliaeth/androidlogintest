package com.goliaeth.logintestapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.goliaeth.logintestapp.databinding.FragmentLoginBinding
import com.goliaeth.logintestapp.data.network.AuthAPI
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.repository.AuthRepository
import com.goliaeth.logintestapp.ui.base.BaseFragment
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    //@todo onActivityCreated is deprecated, see LifecycleObserver for the activity Lifecycle
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        userPreferences.saveAuthToken(it.value.token)
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login failure", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginTextEdit.text.toString().trim()
            val password = binding.passwordTextEdit.text.toString().trim()
            //@todo add input validations
            viewModel.login(email, password)
        }

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildAPI(AuthAPI::class.java))

}