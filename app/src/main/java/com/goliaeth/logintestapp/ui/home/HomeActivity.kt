package com.goliaeth.logintestapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.goliaeth.logintestapp.R
import com.goliaeth.logintestapp.data.UserPreferences
import com.goliaeth.logintestapp.data.network.RemoteDataSource
import com.goliaeth.logintestapp.data.network.UserAPI
import com.goliaeth.logintestapp.data.repository.UserRepository
import com.goliaeth.logintestapp.ui.auth.AuthActivity
import com.goliaeth.logintestapp.ui.startNewActivity
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    lateinit var userPreferences: UserPreferences
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userPreferences = UserPreferences(this)
        val remoteDataSource = RemoteDataSource()
        val api = remoteDataSource.buildAPI(UserAPI::class.java,this)
        val authRepository = UserRepository(api)
        viewModel = HomeViewModel(authRepository)
    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }
}