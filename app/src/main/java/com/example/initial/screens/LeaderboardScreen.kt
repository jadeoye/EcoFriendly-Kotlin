// LeaderboardScreen.kt

package com.example.initial.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.initial.R
import com.example.initial.helpers.nunitoSansFont
import com.example.initial.helpers.primary_color
import com.example.initial.persistence.entities.Leaderboard
import com.example.initial.viewmodels.leaderboard.LeaderboardViewModel
import com.example.initial.viewmodels.helpers.user.sessions.UserSessionViewModel

@Composable
fun LeaderboardScreen(
    navController: NavController,
    leaderboardViewModel: LeaderboardViewModel,
    userSessionViewModel: UserSessionViewModel
) {
    val leaderboard by leaderboardViewModel.leaderboard.observeAsState(emptyList())
    val currentUser = userSessionViewModel.user.value

    LaunchedEffect(key1 = leaderboardViewModel, key2 = currentUser) {
        currentUser?.let { user ->
            val currentUserLeaderboard = Leaderboard(
                name = user.firstName + " " + user.lastName,
                points = 220, // Replace with the actual points of the user if available
                createdOn = System.currentTimeMillis()
            )
            leaderboardViewModel.fetchLeaderboard(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000, currentUserLeaderboard)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(primary_color)
    ) {
        Column(
            modifier = Modifier
                .background(primary_color)
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 10.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 20.dp, 0.dp, 0.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(10.dp, 0.dp, 10.dp, 0.dp)
                                .clickable { navController.popBackStack() },
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.White,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.leaderboard_icon),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(0.dp, -10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Leaderboard",
                            color = Color.White,
                            fontSize = 23.sp,
                            fontFamily = nunitoSansFont,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
            Card(
                modifier = Modifier
                    .background(primary_color)
                    .fillMaxSize()
                    .size(50.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp, 0.dp)
                    ) {
                        Spacer(modifier = Modifier.size(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Rank",
                                fontWeight = FontWeight.Bold,
                                fontFamily = nunitoSansFont,
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "User",
                                fontWeight = FontWeight.Bold,
                                fontFamily = nunitoSansFont,
                                fontSize = 18.sp,
                                modifier = Modifier.weight(2f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Points",
                                fontWeight = FontWeight.Bold,
                                fontFamily = nunitoSansFont,
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }

                        LazyColumn {
                            itemsIndexed(leaderboard) { index, player ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        fontFamily = nunitoSansFont,
                                        fontSize = 18.sp,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = player.name,
                                        fontFamily = nunitoSansFont,
                                        fontSize = 18.sp,
                                        modifier = Modifier.weight(2f),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = player.points.toString(),
                                        fontFamily = nunitoSansFont,
                                        fontSize = 18.sp,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Divider(color = Color.Gray, thickness = 1.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LeaderboardScreenPreview() {
    val navController = rememberNavController()
    val leaderboardViewModel: LeaderboardViewModel = viewModel()
    LeaderboardScreen(navController, leaderboardViewModel, UserSessionViewModel())
}
