package com.google.exhibitioqrvalidator.presentation.ExhibitionAppGraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.exhibitioqrvalidator.presentation.auth.LoginScreen
import com.google.exhibitioqrvalidator.presentation.auth.RegisterScreen
import com.google.exhibitioqrvalidator.presentation.onBoarding.OnBoardingScreen
import com.google.exhibitioqrvalidator.presentation.onBoarding.OnBoardingViewModel


@Composable
fun NavGraph( startDestination: String,) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination){
        composable(Route.OnBoardingScreen.route){
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(event = viewModel::onEvent)
        }
        composable(Route.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(Route.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        composable(Route.AppStartDestination.route){
            Text("Start Destination")
        }
    }
}