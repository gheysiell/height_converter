package com.example.height_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.height_converter.ui.theme.Height_converterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Height_converterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HeightConverterApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeightConverterApp() {
    var heightCm by remember { mutableStateOf("") }
    var resultMeters by remember { mutableStateOf("") }
    var resultFeetInches by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 10.dp) {
                TopAppBar(
                    title = {
                        Text(
                            "Conversor de Altura",
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(0xFF6200EE),
                        titleContentColor = Color.White,
                    ),
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = heightCm,
                onValueChange = {
                    heightCm = it.filter { c -> c.isDigit() }
                },
                label = {
                    Text(
                        "Altura em cm",
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                val cm = heightCm.toDoubleOrNull()
                if (cm != null) {
                    resultMeters = "${cm / 100} m"
                    val feet = cm / 30.48
                    val inches = (feet - feet.toInt()) * 12
                    resultFeetInches = "${feet.toInt()} pés ${inches.toInt()} pol"
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Converter")
            }

            if (resultMeters.isNotEmpty()) {
                Text(
                    "Em metros: $resultMeters",
                )
                Text(
                    "Em pés/polegadas: $resultFeetInches",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Height_converterTheme {
        HeightConverterApp()
    }
}
