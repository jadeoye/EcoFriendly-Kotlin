package com.example.initial.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.initial.R
import com.example.initial.helpers.nunitoSansFont
import com.example.initial.helpers.primary_color
import com.example.initial.persistence.entities.joins.ExchangeableWallet
import com.example.initial.viewmodels.donations.DonationsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DonationsScreen(navController: NavController, donationsViewModel: DonationsViewModel) {
    var donations by remember { mutableStateOf(listOf<ExchangeableWallet>()) }

    LaunchedEffect(key1 = donationsViewModel) {
        donations = donationsViewModel.listDonations()
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
                            modifier = Modifier.size(100.dp),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            painter = painterResource(id = R.drawable.donations)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(0.dp, -10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Donations",
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

                        LazyColumn {
                            items(donations) { donation ->
                                val exchangeable = donation.exchangeable
                                val wallet = donation.wallet
                                val category = donation.category
                                val dateFormat =
                                    SimpleDateFormat("dd MMM yyyy, hh:mm:ss a", Locale.getDefault())
                                val formattedDate = dateFormat.format(Date(exchangeable.createdOn))

                                Row(modifier = Modifier) {
                                    val bitmap = decodeBase64ToBitmap(exchangeable.photo)
                                    if (bitmap != null) {
                                        Image(
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(0.dp, 10.dp, 0.dp, 0.dp),
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = ""
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(20.dp))

                                    Column {
                                        Text(
                                            text = "Name: ${exchangeable.name}",
                                            fontFamily = nunitoSansFont,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Category: ${category.name}",
                                            fontFamily = nunitoSansFont,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Donated: $formattedDate",
                                            fontFamily = nunitoSansFont,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Points earned: ${category.points}",
                                            fontFamily = nunitoSansFont,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = "Wallet's Total: ${wallet.amount}",
                                            fontFamily = nunitoSansFont,
                                            fontSize = 14.sp
                                        )
                                    }
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(0.dp, 20.dp),
                                    thickness = DividerDefaults.Thickness
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        ) {
                            Image(
                                alpha = 0.2f,
                                contentScale = ContentScale.Fit,
                                contentDescription = "",
                                alignment = Alignment.Center,
                                painter = painterResource(id = R.drawable.grass)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}


@Preview
@Composable
fun DonationsScreenPreview() {
    // DonationsScreen(navController = rememberNavController())
}