package com.example.weatherforecast.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Fragment.collectFlow(flow: Flow<T?>, onCollect: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { value ->
                value?.let {
                    onCollect(value)
                }
            }
        }
    }
}