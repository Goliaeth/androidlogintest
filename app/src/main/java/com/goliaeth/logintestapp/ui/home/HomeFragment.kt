package com.goliaeth.logintestapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goliaeth.logintestapp.data.network.Resource
import com.goliaeth.logintestapp.data.network.UserAPI
import com.goliaeth.logintestapp.data.repository.UserRepository
import com.goliaeth.logintestapp.data.responces.Data
import com.goliaeth.logintestapp.databinding.FragmentHomeBinding
import com.goliaeth.logintestapp.ui.auth.ActivityLifeCycleObserver
import com.goliaeth.logintestapp.ui.base.BaseFragment
import com.goliaeth.logintestapp.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(ActivityLifeCycleObserver {

            binding.progressbar.visible(false)

            viewModel.getUser()

            viewModel.user.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        binding.progressbar.visible(false)
                        updateUI(it.value.data)
                    }
                    is Resource.Loading -> {
                        binding.progressbar.visible(true)
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "Request failure", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun updateUI(data: Data) {
        with (binding) {
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