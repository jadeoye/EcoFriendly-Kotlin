package com.example.initial.helpers

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.initial.R

val openSansFont = FontFamily(
    Font(R.font.opensans_regular),
    Font(R.font.opensans_bold, FontWeight.Bold),
    Font(R.font.opensans_light, FontWeight.Light),
    Font(R.font.opensans_medium, FontWeight.Medium)
)

val nunitoSansFont = FontFamily(
    Font(R.font.nunitosans_10pt_regular),
    Font(R.font.nunitosans_10pt_bold, FontWeight.Bold),
    Font(R.font.nunitosans_10pt_light, FontWeight.Light),
    Font(R.font.nunitosans_10pt_medium, FontWeight.Medium)
)