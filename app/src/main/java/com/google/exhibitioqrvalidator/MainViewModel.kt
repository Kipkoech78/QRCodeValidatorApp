package com.google.exhibitioqrvalidator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.exhibitioqrvalidator.domain.manager.usecases.AppEntryUseCases
import com.google.exhibitioqrvalidator.presentation.ExhibitionAppGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        appEntryPoint.ReadAppEntry().onEach { hasFinishedOnboarding ->
         //   val currentUser = FirebaseAuth.getInstance().currentUser
            startDestination = when {
                !hasFinishedOnboarding -> Route.OnBoardingScreen.route
           //     currentUser != null -> Route.AppStartDestination.route
                else -> Route.LoginScreen.route
            }
            delay(600)
            SplashCondition = false
        }.launchIn(viewModelScope)
    }


}