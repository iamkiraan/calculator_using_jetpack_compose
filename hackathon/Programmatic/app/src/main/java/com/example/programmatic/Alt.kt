package com.example.programmatic

data class Alt(
    val default: String,
    val description: String,
    val `enum`: List<String>,
    val enumDescriptions: List<String>,
    val location: String,
    val type: String
)