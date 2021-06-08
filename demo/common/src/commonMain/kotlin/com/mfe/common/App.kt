package com.mfe.common
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    var link by remember { mutableStateOf("....") }
    MaterialTheme {
        Column(Modifier.background(Color.LightGray)) {
            Button(onClick = {
                text = "Hello, ${getPlatformName()}"
            }) {
                Text(text)
            }
            Spacer(Modifier.height(40.dp))
            Text( link, fontSize=30.sp, fontWeight= FontWeight.SemiBold, fontStyle= FontStyle.Italic, color= Color.Blue,)
        }
    }
}
