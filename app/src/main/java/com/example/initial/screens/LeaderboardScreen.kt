package com.example.initial.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    val currentUser = userSessionViewModel.user.observeAsState().value

    // Add the logged-in user to the leaderboard if not already present
    val leaderboardWithCurrentUser = leaderboard.toMutableList().apply {
        if (currentUser != null && none { it.name == currentUser.firstName }) {
            add(
                Leaderboard(
                    name = currentUser.firstName,
                    points = 0,
                    createdOn = System.currentTimeMillis() // Add this line to set createdOn
                )
            )
        }
    }

    LaunchedEffect(key1 = leaderboardViewModel) {
        leaderboardViewModel.fetchLeaderboard(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000) // Last 7 days
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
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "User",
                                fontWeight = FontWeight.Bold,
                                fontFamily = nunitoSansFont,
                                fontSize = 18.sp,
                                modifier = Modifier.weight(2f)
                            )
                            Text(
                                text = "Points",
                                fontWeight = FontWeight.Bold,
                                fontFamily = nunitoSansFont,
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        LazyColumn {
                            items(leaderboardWithCurrentUser.sortedByDescending { it.points }) { player ->
                                val isCurrentUser = player.name == currentUser?.firstName
                                val backgroundColor =
                                    if (isCurrentUser) Color.LightGray else Color.Transparent

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .background(backgroundColor)
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${leaderboardWithCurrentUser.indexOf(player) + 1}",
                                        fontFamily = nunitoSansFont,
                                        fontSize = 18.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = player.name,
                                        fontFamily = nunitoSansFont,
                                        fontSize = 18.sp,
                                        fontWeight = if (isCurrentUser) FontWeight.Bold else FontWeight.Normal,
                                        modifier = Modifier.weight(2f)
                                    )
                                    Text(
                                        text = player.points.toString(),
                                        fontFamily = nunitoSansFont,
                                        fontSize = 18.sp,
                                        fontWeight = if (isCurrentUser) FontWeight.Bold else FontWeight.Normal,
                                        modifier = Modifier.weight(1f)
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
    val userSessionViewModel: UserSessionViewModel = viewModel()
    LeaderboardScreen(navController, leaderboardViewModel, userSessionViewModel)
}
