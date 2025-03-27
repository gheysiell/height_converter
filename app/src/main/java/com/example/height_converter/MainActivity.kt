package com.example.height_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var height by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("CM") }
    var result by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    fun calculateResult() {
        val value = height.toDoubleOrNull()
        if (value != null) {
            result = if (unit == "CM") {
                val totalInches = value / 2.54
                val feet = totalInches / 12
                val inches = totalInches % 12
                "${feet.toInt()} pés ${inches.toInt()} pol"
            } else {
                val feetInches = value * 30.48
                "${String.format("%.2f", feetInches)} cm"
            }
        } else {
            result = ""
        }
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 10.dp,
            ) {
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
                value = height,
                onValueChange = {
                    height = it.filter { c -> c.isDigit() || c == '.' }
                    calculateResult()
                },
                label = {
                    Text(
                        "Altura em ${if (unit == "CM") "CM" else "Feet/Inches"}",
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    value = unit,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    label = {
                        Text(
                            "Unidade",
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                        )
                    },
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                "CM",
                            )
                        },
                        onClick = {
                            if (unit != "CM" && height.isNotEmpty()) {
                                height = String.format("%.2f", height.toDouble() * 30.48)
                            }
                            unit = "CM"
                            expanded = false
                            calculateResult()
                        },
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Feet/Inches",
                            )
                        },
                        onClick = {
                            if (unit != "Feet/Inches" && height.isNotEmpty()) {
                                height = String.format("%.2f", height.toDouble() / 30.48)
                            }
                            unit = "Feet/Inches"
                            expanded = false
                            calculateResult()
                        },
                    )
                }
            }

            Text(
                "Convertido: ${if (result.toString().isNotEmpty()) result else ""} ",
            )
        }
    }
}