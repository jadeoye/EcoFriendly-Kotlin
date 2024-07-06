package com.example.initial.helpers

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppSnackBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
//    SnackbarHost(
//        hostState = snackbarHostState,
//        snackbar = { data ->
//            Snackbar (
//                modifier = Modifier,
//                action = {
//                    data.actionLabel?.let {
//                        actionLabel ->
//                        TextButton(onClick = { onDismiss() }) {
//                            Text(text = actionLabel)
//                        }
//                    }
//                }
//            )
//            {
////Text(text = data.message)
//            }
    //})
}