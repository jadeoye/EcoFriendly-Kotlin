package com.example.initial.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.initial.R
import com.example.initial.helpers.android_padding_top
import com.example.initial.helpers.nunitoSansFont
import com.example.initial.helpers.primary_color
import com.example.initial.viewmodels.login.LoginViewModel
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel, userSessionViewModel: UserSessionViewModel) {
    var email by remember { mutableStateOf("user") }
    var password by remember { mutableStateOf("123") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginResult by remember { mutableStateOf<Boolean?>(null) }
    val isLoading by viewModel.isLoading.observeAsState(false)
    val loginError by viewModel.loginErrorMessage.observeAsState(null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, android_padding_top, 20.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp), contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier,
                    contentScale = ContentScale.Fit,
                    contentDescription = "",
                    alignment = Alignment.Center,
                    painter = painterResource(id = R.drawable.pear)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Log into your account",
                fontFamily = nunitoSansFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Please enter your login details to continue",
                fontFamily = nunitoSansFont,
                fontWeight = FontWeight.Light,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Default.FavoriteBorder else Icons.Default.Favorite

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, "")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primary_color),
                onClick = {
                    viewModel.authenticate(email, password) { user ->
                        if (user != null && user.id > 0) {
                            navController.navigate("home")
                            userSessionViewModel.login(user)
                        }
                    }
                }) {
                Text(text = "Sign In")
            }

            if (isLoading) {
                CircularProgressIndicator()
            }

            loginError?.let { error ->
                Snackbar(modifier = Modifier, action = {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Retry")
                    }
                }) {
                    Text(text = error)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // LoginScreen(navController = rememberNavController())
}