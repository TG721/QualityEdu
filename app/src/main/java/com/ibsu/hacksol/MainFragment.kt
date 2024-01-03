package com.ibsu.hacksol

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.hacksol.adapters.CourseAndAcademyAdapter
import com.ibsu.hacksol.adapters.ProgramAndUniversityAdapter
import com.ibsu.hacksol.databinding.FragmentMainBinding
import com.ibsu.hacksol.dto.Course
import com.ibsu.hacksol.dto.CourseAndAcademy
import com.ibsu.hacksol.dto.Program
import com.ibsu.hacksol.dto.ProgramAndUniversity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private lateinit var rvCoursesAdapter: CourseAndAcademyAdapter
    private lateinit var rvProgramsAdapter: ProgramAndUniversityAdapter
    var courses: MutableList<Course> = mutableListOf<Course>()
    var programs: MutableList<Program> = mutableListOf<Program>()
    override fun setup() {
        getCourses()
    }

    private fun getCourses() {
        binding.searchView.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        val call: Call<CourseAndAcademy> = ApiService.api.getCoursesData()

        call.enqueue(object : Callback<CourseAndAcademy> {
            override fun onResponse(
                call: Call<CourseAndAcademy>,
                response: Response<CourseAndAcademy>,
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    val data: CourseAndAcademy? = response.body()
                    // Do something with the data


                    courses.clear()
                    insertCoursesIntoRV(data)
                    binding.searchView.isEnabled = true
                } else {
                    // Handle error
                    // Handle error
//                    programs.clear()
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Could not load course data",
                        Toast.LENGTH_LONG).show();
                }
            }

            override fun onFailure(call: Call<CourseAndAcademy>, t: Throwable) {
                // Handle failure

                t.message
            }
        })
    }

    private fun getPrograms() {
        binding.searchView.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        val call: Call<ProgramAndUniversity> = ApiService.api.getProgramsData()

        call.enqueue(object : Callback<ProgramAndUniversity> {
            override fun onResponse(
                call: Call<ProgramAndUniversity>,
                response: Response<ProgramAndUniversity>,
            ) {
                if (response.isSuccessful) {
                    val data: ProgramAndUniversity? = response.body()
                    // Do something with the data
                    programs.clear()
                    binding.progressBar.visibility = View.GONE
                    insertProgramsIntoRV(data)
                    binding.searchView.isEnabled = true
                } else {
                    // Handle error
//                    programs.clear()
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Could not load program data",
                        Toast.LENGTH_LONG).show();


                }
            }

            override fun onFailure(call: Call<ProgramAndUniversity>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun insertCoursesIntoRV(data: CourseAndAcademy?) {
        rvCoursesAdapter = CourseAndAcademyAdapter(requireContext())
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = rvCoursesAdapter
        binding.rv.layoutManager = layoutManager
        courses = data!!.courses.toMutableList()
        data?.let {
            rvCoursesAdapter.submitList(it.courses)

        }

    }

    private fun insertProgramsIntoRV(data: ProgramAndUniversity?) {
        rvProgramsAdapter = ProgramAndUniversityAdapter(requireContext())
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = rvProgramsAdapter
        binding.rv.layoutManager = layoutManager
        programs = data!!.programs.toMutableList()
        data?.let {
            rvProgramsAdapter.submitList(it.programs)
        }

    }

    override fun listeners() {
        super.listeners()
        binding.btnCourses.setOnClickListener {
            getCourses()
        }
        binding.btnPrograms.setOnClickListener {
            getPrograms()
        }

        val searchView = binding.searchView
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(courses.size>0 && binding.rv.adapter == rvCoursesAdapter){
                    val filteredList = ArrayList<Course>()

                    for (item in courses){
                        for (item2 in item.tag!!){
                            if (item2.lowercase().contains(newText.toString().lowercase()) )
                                filteredList.add(item)
                        }
                        if ( item.courseName.lowercase().contains(newText.toString().lowercase())  && !filteredList.contains(item))
                            filteredList.add(item)
                    }
                    rvCoursesAdapter.submitList(filteredList)
                } else if(programs.size>0 && binding.rv.adapter == rvProgramsAdapter){

                    val filteredList = ArrayList<Program>()
                    for (itemProgram in programs)
                        if ( itemProgram.programName.lowercase().contains(newText.toString().lowercase()) || itemProgram.universityName.lowercase().contains(newText.toString().lowercase()))
                        filteredList.add(itemProgram)
                    rvProgramsAdapter.submitList(filteredList)
                }
                return false
            }

        })

    }

}