package com.goliaeth.logintestapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.network.UserAPI
import com.goliaeth.logintestapp.data.repository.UserRepository
import com.goliaeth.logintestapp.data.responces.User
import com.goliaeth.logintestapp.databinding.FragmentHomeBinding
import com.goliaeth.logintestapp.ui.base.BaseFragment
import com.goliaeth.logintestapp.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<HomeVieModel, FragmentHomeBinding, UserRepository>() {

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
    }

    private fun updateUI(user: User) {
        with(binding) {
            idTextView.text = user.id.toString()
            emailTextView.text = user.email
            firstNameTextView.text = user.first_name
            lastNameTextView.text = user.last_name
        }
    }

    override fun getViewModel() = HomeVieModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildAPI(UserAPI::class.java, token)
        return UserRepository(api)
    }


}