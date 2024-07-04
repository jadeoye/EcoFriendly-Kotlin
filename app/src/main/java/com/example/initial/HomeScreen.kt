package com.example.initial

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(red = 153, green = 204, blue = 255))
                    .border(1.dp, Color.Transparent, RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate("give")
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "",
                        tint = Color.White)
                    Text(
                        text = "Give",
                        color = Color.White)
                }
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(red = 255, green = 204, blue = 103))
                    .border(1.dp, Color.Transparent, RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate("donations")
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = Color.White)
                    Text(
                        text = "Donations",
                        color = Color.White)
                }
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(red = 153, green = 255, blue = 153))
                    .border(1.dp, Color.Transparent, RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate("wallet")
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = Color.White)
                    Text(
                        text = "Wallet",
                        color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}