package com.example.myapplication.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.session.UserSession
import com.example.myapplication.viewmodel.RecetasViewModel

class RecetasViewModelFactory(
    private val context: Context,
    private val session: UserSession
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetasViewModel::class.java)) {
            return RecetasViewModel(context, session) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
