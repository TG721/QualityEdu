package com.ibsu.hacksol.dto

data class Course(
    val __v: Int,
    val _id: String,
    val academyName: String,
    val courseCost: Int,
    val courseLecturers: List<String>?,
    val courseName: String,
    val duration: Int?,
    val id: Int,
    val imageURL: String,
    val syllabusLink: String?,
    val tag: List<String>?,
    val usedTools: List<String>?
)