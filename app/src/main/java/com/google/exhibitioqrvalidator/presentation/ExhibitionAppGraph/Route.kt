package com.google.exhibitioqrvalidator.presentation.ExhibitionAppGraph

sealed class Route(var route:String) {
    object HomeScreen: Route(route= "Homescreen")
    object AppStartDestination: Route(route = "AppStartDestination")
    object OnBoardingScreen: Route(route = "OnBoardingScreen")
    object LoginScreen: Route(route = "LoginScreen")
    object RegisterScreen: Route(route = "RegisterScreen")
    object AttendanceList: Route(route = "AttendanceList")
    object QRCodeNavigator: Route(route = "QRCodeNavigator")
 //   object RegisterScreen: Route(route = "RegisterScreen")

}