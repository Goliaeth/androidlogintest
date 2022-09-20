package com.goliaeth.logintestapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.network.UserAPI
import com.goliaeth.logintestapp.data.repository.UserRepository
import com.goliaeth.logintestapp.data.responces.Data
import com.goliaeth.logintestapp.databinding.FragmentHomeBinding
import com.goliaeth.logintestapp.ui.base.BaseFragment
import com.goliaeth.logintestapp.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.visible(false)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.value.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "user response failure", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(data: Data) {
        with(binding) {
            idTextView.text = data.id.toString()
            emailTextView.text = data.email
            firstNameTextView.text = data.first_name
            lastNameTextView.text = data.last_name
        }
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        //@todo don`t use runBlocking
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildAPI(UserAPI::class.java, token)
        return UserRepository(api)
    }


}