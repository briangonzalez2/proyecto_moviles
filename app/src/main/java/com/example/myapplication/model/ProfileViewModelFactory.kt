package com.example.myapplication.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.session.UserSession
import com.example.myapplication.viewmodel.ProfileViewModel

class ProfileViewModelFactory(
    private val context: Context,
    private val session: UserSession
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(context, session) as T
    }
}