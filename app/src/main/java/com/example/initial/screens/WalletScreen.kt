package com.example.initial.screens

import androidx.activity.ComponentActivity
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.initial.R
import com.example.initial.helpers.nunitoSansFont
import com.example.initial.helpers.primary_color
import com.example.initial.persistence.entities.joins.ExchangeableWallet
import com.example.initial.viewmodels.wallets.WalletViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WalletScreen(navController: NavController, walletViewModel: WalletViewModel) {
    var walletPoints by remember { mutableStateOf(0) }
    var cashRedeemable by remember { mutableStateOf((0.0)) }
    val context = LocalContext.current

    LaunchedEffect(key1 = walletViewModel) {
        walletPoints = walletViewModel.getTotalPoints()
        cashRedeemable = walletViewModel.getRedeemableAmount(walletPoints)
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
                        modifier = Modifier.fillMaxWidth(),
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
                            text = "Wallet",
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
                        modifier = Modifier.padding(20.dp, 0.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.size(20.dp))

                        Text(text = "Redeemable points you have", fontFamily = nunitoSansFont)

                        Text(
                            text = "${walletPoints}",
                            fontFamily = nunitoSansFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 100.sp,
                            color = primary_color
                        )

                        val annotatedLabelString = buildAnnotatedString {
                            append("This can redeem")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(" $${cashRedeemable} ")
                            }
                            append("in voucher value.")
                        }

                        val annotatedButtonString = buildAnnotatedString {
                            append("Convert")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(" $${cashRedeemable} ")
                            }
                            append("to voucher value")
                        }

                        Text(text = annotatedLabelString, fontFamily = nunitoSansFont)

                        Spacer(modifier = Modifier.size(20.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Column(modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                    (context as ComponentActivity).lifecycleScope.launch {
                                        walletViewModel.redeemPoints()
                                    }
                                }) {
                                    Text(text = annotatedButtonString, fontSize = 17.sp, fontFamily = nunitoSansFont)
                                }

                                Spacer(modifier = Modifier.size(20.dp))

                                Text(
                                    modifier = Modifier.clickable { navController.navigate("vouchers") },
                                    text = "View Vouchers",
                                    style = TextStyle(textDecoration = TextDecoration.Underline),
                                    fontWeight = FontWeight.Light,
                                    fontFamily = nunitoSansFont,
                                    fontSize = 17.sp
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

@Preview
@Composable
fun WalletScreenPreview() {
    // WalletScreen(navController = rememberNavController())
}