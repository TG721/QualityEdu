package com.ibsu.hacksol.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibsu.hacksol.R
import com.ibsu.hacksol.databinding.ProgramItemBinding
import com.ibsu.hacksol.dto.Program
import com.ibsu.hacksol.extensions.loadFromResource
import com.ibsu.hacksol.extensions.loadFromUrl


class ProgramAndUniversityAdapter(private val context: Context) :
    ListAdapter<Program, ProgramAndUniversityAdapter.ProgramItemViewHolder>(ItemDiffCallback()) {
    inner class ProgramItemViewHolder(private val binding: ProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            val source = getItem(absoluteAdapterPosition)
            binding.apply {
                val imgLink = source.imageURL
                tvUniversityName.text = source.universityName
                tvDegree.text = source.degree
                tvCost.text = source.programCost.toString() + "₾"
                d("kkk",(imgLink=="assets/ibsu.jpg").toString())
                d("mmm",(imgLink).toString())
                d("lll",(imgLink).toString())

                when {
                    source.imageURL == "assets/tsu.png" -> {
                        imageView.setImageResource(R.drawable.tsu)
                    }

                    source.imageURL == "assets/ilia.jpg" -> {
                        imageView.setImageResource(R.drawable.ilia)
                    }

                    source.imageURL == "assets/ibsu.jpg" -> {
                        imageView.setImageResource(R.drawable.ibsu)
                    }

                    source.imageURL == "assets/FreeUniLogo.png" -> {
                        imageView.setImageResource(R.drawable.freeunilogo)
                    }

                    source.imageURL == "assets/btu.png" -> {
                        imageView.setImageResource(R.drawable.btu)
                    }
                }

                programName.text = source.programName
                btnCurriculum.setOnClickListener {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(source.curriculumLink)
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProgramItemBinding.inflate(layoutInflater, parent, false)
        return ProgramItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramItemViewHolder, position: Int) {
        holder.bind()
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Program>() {
        override fun areItemsTheSame(
            oldItem: Program,
            newItem: Program,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Program,
            newItem: Program,
        ): Boolean =
            oldItem == newItem

    }
}