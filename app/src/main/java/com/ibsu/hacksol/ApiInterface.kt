package com.ibsu.hacksol

import com.ibsu.hacksol.dto.CourseAndAcademy
import com.ibsu.hacksol.dto.ProgramAndUniversity
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("courses/")
    fun getCoursesData(): Call<CourseAndAcademy>
    @GET("programs/")
    fun getProgramsData(): Call<ProgramAndUniversity>
}
