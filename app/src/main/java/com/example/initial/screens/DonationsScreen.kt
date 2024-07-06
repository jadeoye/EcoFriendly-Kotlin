package com.example.initial.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DonationsScreen(navController: NavController){

}

@Preview
@Composable
fun DonationsScreenPreview(){
    DonationsScreen(navController = rememberNavController())
}