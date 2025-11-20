package com.google.exhibitioqrvalidator.presentation.QrCodeNavigator

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.exhibitioqrvalidator.R
import com.google.exhibitioqrvalidator.presentation.ExhibitionAppGraph.Route
import com.google.exhibitioqrvalidator.presentation.attendees.AttendanceList
import com.google.exhibitioqrvalidator.presentation.home.HomeScreen

@Composable
fun QRCodeNavigator() {
    val BottomNavigationItems = remember {
        listOf(
            BottomNavItems(icon = R.drawable.outline_qr_code_scanner_24, text = "scanner"),
            BottomNavItems(icon = R.drawable.ic_preferences, text = "Listing"),
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = remember(key1 = backStackState) {
        when(backStackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.AttendanceList.route ->1
         //   Route.ProbationsScreen.route -> 2

            else -> 0
        }
    }
    val isNavBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.AttendanceList.route


    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isNavBarVisible) {
                BottomNavigation(items = BottomNavigationItems,
                    selected = selectedItem,
                    onItemClicked = { index ->
                        when (index) {
                            0 -> {
                                navigateToTap(
                                    navController = navController,
                                    route = Route.HomeScreen.route
                                )
                            }
                            1 -> {
                                navigateToTap(
                                    navController = navController,
                                    route = Route.AttendanceList.route
                                )
                            }
//                            2 -> {
//                                navigateToTap(
//                                    navController = navController,
//                                    route = Route.ProbationsScreen.route
//                                )
//                            }


                        }

                    })
            }

        }
    )
    {
        val BottomPadding = it.calculateBottomPadding()
        NavHost(navController = navController, startDestination = Route.HomeScreen.route ,
            modifier = Modifier.padding(bottom = 6.dp)) {
            composable(route = Route.HomeScreen.route){
                HomeScreen(navController)
            }
            composable(route = Route.AttendanceList.route){
                AttendanceList(navController )
            }

//            composable(
//                route = Route.DashboardScreen.route,
//                arguments = listOf(navArgument("offenderId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val offenderId = backStackEntry.arguments?.getString("offenderId") ?: ""
//
//                // Retrieve offender from ViewModel by ID
//                val viewModel: OffenderCRUDViewModel = hiltViewModel()
//                val offender = viewModel.getOffenderById(offenderId)
//
//                if (offender != null) {
//                    DashboardScreen(
//
//                        offender = offender,
//                        onSave = { updated ->
//                            viewModel.updateOffender(updated)
//                            navController.popBackStack() // go back after save
//                        }
//                    )
//                }
//            }
            composable(Route.LoginScreen.route) {
                //  LoginScreen()
            }
        }
    }
}
fun navigateToTap(navController: NavController, route: String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let {homeScreen ->
            popUpTo(homeScreen){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true

        }
    }
}
@Composable
fun BottomNavigation(items: List<BottomNavItems>,
                     selected: Int,
                     onItemClicked: (Int) -> Unit,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 3.dp) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(selected = index == selected ,
                onClick = { onItemClicked(index) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = item.icon), contentDescription = null,
                            modifier = Modifier.size(30.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.body),
                    unselectedTextColor = colorResource(id = R.color.body),
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}
data class BottomNavItems(
    @DrawableRes val icon: Int,
    val text: String
)