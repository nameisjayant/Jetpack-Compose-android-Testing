package com.programmingsimplified.testinginandroid.common

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog


@Composable
fun CommonProgress() {
    Dialog(onDismissRequest = {  }) {
        CircularProgressIndicator()
    }
}