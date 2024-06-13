package com.example.programmatic

data class PropertyState(
    val description: String,
    val `enum`: List<String>,
    val enumDescriptions: List<String>,
    val type: String
)