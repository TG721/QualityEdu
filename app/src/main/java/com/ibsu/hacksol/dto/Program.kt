package com.ibsu.hacksol.dto

data class Program(
    val __v: Int,
    val _id: String,
    val courseLecturers: List<String>,
    val curriculumLink: String,
    val degree: String,
    val id: Int,
    val imageURL: String,
    val programCost: Int,
    val programName: String,
    val tag: List<String>,
    val universityName: String
)