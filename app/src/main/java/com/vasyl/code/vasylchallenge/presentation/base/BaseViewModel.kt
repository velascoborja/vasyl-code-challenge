package com.vasyl.code.vasylchallenge.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasyl.code.vasylchallenge.presentation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _navigateFlow = MutableSharedFlow<Screen>()
    val navigateFlow = _navigateFlow.asSharedFlow()

    protected fun navigate(screen: Screen) {
        viewModelScope.launch {
            _navigateFlow.emit(screen)
        }
    }
}