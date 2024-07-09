package com.example.initial.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.initial.R
import com.example.initial.helpers.nunitoSansFont
import com.example.initial.helpers.primary_color
import com.example.initial.persistence.entities.Category
import com.example.initial.viewmodels.give.GiveViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiveScreen(navController: NavController, giveViewModel: GiveViewModel) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(listOf<Category>()) }
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }
    var categoriesExpanded by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var base64String by remember { mutableStateOf<String?>(null) }
    var galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            base64String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)
                bitmapToBase64(bitmap)
            } else {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                bitmapToBase64(bitmap)
            }
        }
    }

    LaunchedEffect(key1 = giveViewModel) {
        categories = giveViewModel.listCategories()
        selectedCategory = categories.first()
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
                            painter = painterResource(id = R.drawable.give)
                        )
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .offset(0.dp, -10.dp),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = "Give",
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

                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Item Name") },
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.size(20.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            ExposedDropdownMenuBox(expanded = categoriesExpanded,
                                onExpandedChange = { categoriesExpanded = !categoriesExpanded }) {
                                OutlinedTextField(modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                    readOnly = true,
                                    value = selectedCategory?.name ?: "--",
                                    onValueChange = {},
                                    label = { Text("Category") },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriesExpanded)
                                    })

                                ExposedDropdownMenu(modifier = Modifier.fillMaxWidth(),
                                    expanded = categoriesExpanded,
                                    onDismissRequest = { categoriesExpanded = false }) {
                                    categories.forEach { item ->
                                        DropdownMenuItem(modifier = Modifier.fillMaxWidth(),
                                            text = { Text(text = item.name) },
                                            onClick = {
                                                selectedCategory = item
                                                categoriesExpanded = false
                                            })
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.size(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(0.dp, 10.dp, 5.dp, 0.dp),
                                text = "Select Item's Photo:",
                                fontFamily = nunitoSansFont,
                            )
                            IconButton(modifier = Modifier,
                                onClick = { galleryLauncher.launch("image/*") }) {
                                Icon(
                                    modifier = Modifier.size(80.dp),
                                    painter = painterResource(id = R.drawable.camera),
                                    contentDescription = "",
                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(20.dp))

                        selectedImageUri?.let { uri ->
                            Column {
                                Text(
                                    text = "Preview", fontFamily = nunitoSansFont
                                )

                                Spacer(modifier = Modifier.size(10.dp))

                                Image(
                                    modifier = Modifier.size(100.dp),
                                    contentScale = ContentScale.Fit,
                                    contentDescription = "",
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(LocalContext.current).data(uri)
                                            .build()
                                    )
                                )
                            }
                        }

                        Button(modifier = Modifier, onClick = {
                            if (name.isNotEmpty() && selectedCategory!!.id > 0 && base64String != null) {
                                (context as ComponentActivity).lifecycleScope.launch {
                                    giveViewModel.give(
                                        name, selectedCategory!!, base64String!!
                                    )

                                    name = ""
                                    base64String = ""
                                    selectedImageUri = null
                                    selectedCategory = categories.first()

                                    Toast.makeText(
                                        context, "Donation Submitted", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context, "All fields are required", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Text(text = "Give", fontFamily = nunitoSansFont)
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

fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

@Preview
@Composable
fun GiveScreenPreview() {
//    val databse = AppDatabase.getDatabase()
//    val iCategory =
//    val categoryRepository = CategoryRepository()
//    val
//    GiveScreen(navController = rememberNavController(), giveViewModel = GiveViewModel())
}