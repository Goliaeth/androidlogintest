package com.goliaeth.logintestapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.goliaeth.logintestapp.R
import com.goliaeth.logintestapp.data.network.RemoteDataSource
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.network.UserAPI
import com.goliaeth.logintestapp.data.repository.UserRepository
import com.goliaeth.logintestapp.data.responces.Data
import com.goliaeth.logintestapp.databinding.FragmentHomeBinding
import com.goliaeth.logintestapp.ui.handleApiError
import com.goliaeth.logintestapp.ui.logout
import com.goliaeth.logintestapp.ui.visible

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val remoteDataSource = RemoteDataSource()
        val api = remoteDataSource.buildAPI(UserAPI::class.java, requireContext())
        val userRepository = UserRepository(api)
        viewModel = HomeViewModel(userRepository)

        binding.progressbar.visible(false)
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.value.data)
                }
                is Resource.Failure -> {
                    handleApiError(it)
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

}