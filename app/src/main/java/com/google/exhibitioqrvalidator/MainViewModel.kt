package com.google.exhibitioqrvalidator

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.exhibitioqrvalidator.domain.manager.usecases.AppEntryUseCases
import com.google.exhibitioqrvalidator.presentation.ExhibitionAppGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryPoint: AppEntryUseCases
) : ViewModel() {

    var SplashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.OnBoardingScreen.route)
        private set

    init {
        viewModelScope.launch {

            combine(
                appEntryPoint.ReadAppEntry(),
                appEntryPoint.ReadToken()
            ) { hasOnboarded, token ->
                when {
                    !hasOnboarded -> Route.OnBoardingScreen.route
                    !token.isNullOrBlank()-> Route.AppStartDestination.route
                    else -> Route.LoginScreen.route
                }
            }.collect { destination ->
                Log.d("navigation", "Routing to: $destination")
                startDestination = destination
                delay(300)
                SplashCondition = false
            }
        }

    }


}