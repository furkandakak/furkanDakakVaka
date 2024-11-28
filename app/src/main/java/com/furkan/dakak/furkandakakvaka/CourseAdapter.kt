package com.furkan.dakak.furkandakakvaka

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkan.dakak.furkandakakvaka.databinding.ItemCourseBinding

class CourseAdapter(
    private var courses: List<String>,
    private val onEnrollClick: (String) -> Unit,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

   
    fun updateData(newCourses: List<String>) {
        courses = newCourses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val courseUrl = courses[position]
        holder.bind(courseUrl)
    }

    override fun getItemCount(): Int = courses.size

    inner class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

 
        fun bind(courseUrl: String) {
            Log.d("CourseAdapter", "Binding URL: $courseUrl at position $bindingAdapterPosition")

            binding.tvCourseUrl.text = "${bindingAdapterPosition + 1}. Video"
            binding.btnEnroll.setOnClickListener {
                Log.d("CourseAdapter", "Enroll clicked for URL: $courseUrl")
                onEnrollClick(courseUrl)
            }
            binding.root.setOnClickListener {
                Log.d("CourseAdapter", "Item clicked for URL: $courseUrl")
                onItemClick(courseUrl)
            }
        }
    }
}

