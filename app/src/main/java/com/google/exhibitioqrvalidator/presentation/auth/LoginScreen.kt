package com.google.exhibitioqrvalidator.presentation.auth

import android.R.attr.progress
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.exhibitioqrvalidator.R
import com.google.exhibitioqrvalidator.presentation.ExhibitionAppGraph.Route
import com.google.exhibitioqrvalidator.presentation.common.HeadingTextComponents
import com.google.exhibitioqrvalidator.presentation.common.MyTextFiedComponent
import com.google.exhibitioqrvalidator.presentation.common.PasswordTextField


@Composable
fun LoginScreen(modifier: Modifier = Modifier,navController: NavController ) {
    val viewModel: AuthViewModel = hiltViewModel()
    val loginState by viewModel.loginState.observeAsState()
    val context = LocalContext.current.applicationContext
    var loadingStateinLogin =rememberSaveable { mutableStateOf(false) }
    var password = rememberSaveable { mutableStateOf("") }
    var email = rememberSaveable { mutableStateOf("") }
    // Handle login result

    LaunchedEffect( loginState) {
        when(val state = loginState){
            is UiState.Loading -> {
               loadingStateinLogin.value = true

            }
            is UiState.Error -> {
                loadingStateinLogin.value = false
                Log.d("login error",state.message.toString())
                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
            }
            is UiState.Success<*> -> {
                loadingStateinLogin.value = false
                Log.d("login",state.data.toString())
                Toast.makeText(context,"Welcome ${state.data.toString()} 🎉", Toast.LENGTH_LONG )
                    .show()
                navController.navigate(Route.AppStartDestination.route){
                    popUpTo(Route.LoginScreen.route){inclusive = true}
                }
            }
            null -> Unit
        }
        }
    Scaffold( // No TopAppBar
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .imePadding() // prevents shifting when keyboard opens
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // smaller spacer

            Image(
                painter = painterResource(R.drawable.sample_avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))

            HeadingTextComponents(value = "Sign In")

            Spacer(modifier = Modifier.height(12.dp))

            MyTextFiedComponent(
                valueProperty = email,
                labelValue = "Email Address",
                icon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextField(password, text = "Enter Your Password")

            if (loadingStateinLogin.value) {
                CustomerCircularProgressBar()
            } else {
                FilledTonalButton(
                    onClick = {
                        if(isValidEmail(email.value.trim())){
                            viewModel.login(email.value.trim(), password.value.trim())
                        }else{
                            Toast.makeText(context," Please put valid Email", Toast.LENGTH_SHORT ).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Submit")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Have no account? Sign up",
                color = Color.Blue,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Route.RegisterScreen.route)
                    }
                    .padding(vertical = 8.dp)
            )
        }
    }
}
fun isValidEmail(email: String): Boolean {
    return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
fun isValidName(name: String): Boolean {
    return name.isNotBlank() && name.length >= 3 && name.all { it.isLetter()
        //  || it.isWhitespace()
    }
}


@Composable
fun CustomerCircularProgressBar(
    size: Dp = 96.dp,
    strokeWidth: Dp = 12.dp,
    backgroundArcColor: Color = Color.LightGray
) {
    Canvas(modifier = Modifier.size(size)) {
        // Background Arc Implementation
        drawArc(
            color = backgroundArcColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            size = Size(size.toPx(), size.toPx()),
            style = Stroke(width = strokeWidth.toPx())
        )
    }
}

