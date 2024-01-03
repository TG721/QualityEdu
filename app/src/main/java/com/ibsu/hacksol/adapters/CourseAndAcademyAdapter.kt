package com.ibsu.hacksol.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibsu.hacksol.R
import com.ibsu.hacksol.databinding.CourseItemBinding
import com.ibsu.hacksol.dto.Course
import com.ibsu.hacksol.extensions.loadFromResource

class CourseAndAcademyAdapter(private val context: Context) :
    ListAdapter<Course, CourseAndAcademyAdapter.CourseItemViewHolder>(ItemDiffCallback()) {
    inner class CourseItemViewHolder(private val binding: CourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {

                tvAcademyName.text = source.academyName
                tvCourseName.text = source.courseName
                tvCost.text = source.courseCost.toString() + "₾"
                source.duration?.let {
                    tvDuration.text = it.toString() + " Weeks"
                }
                source.courseLecturers?.let {
                    tvLecturers.text = source.courseLecturers.joinToString("\n")
                }
                    when{
                        source.imageURL=="assets/academy.png" -> {
                            Log.d("vvv", "ss")
                            imageView.setImageResource(R.drawable.academy)}
                        source.imageURL=="assets/skillwill.png" -> {imageView.setImageResource(R.drawable.skillwill)}

                        source.imageURL=="assets/cyber.jpg" -> {imageView.setImageResource(R.drawable.cyber)}
                        source.imageURL=="assets/it-step.png" -> {imageView.setImageResource(R.drawable.it_step)}
                }
                btnLearnMore.setOnClickListener {
                    source.syllabusLink?.let {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(source.syllabusLink)
                            )
                        )
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CourseItemBinding.inflate(layoutInflater, parent, false)
        return CourseItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseItemViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(
            oldItem: Course,
            newItem: Course,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Course,
            newItem: Course,
        ): Boolean =
            oldItem == newItem

    }
}