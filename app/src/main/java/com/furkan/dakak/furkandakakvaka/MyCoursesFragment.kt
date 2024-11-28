package com.furkan.dakak.furkandakakvaka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkan.dakak.furkandakakvaka.databinding.FragmentMyCoursesBinding
import com.furkan.dakak.furkandakakvaka.db.AppDatabase
import com.furkan.dakak.furkandakakvaka.mvvm.CourseRepository
import com.furkan.dakak.furkandakakvaka.mvvm.CourseViewModel
import com.furkan.dakak.furkandakakvaka.mvvm.ViewModelFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ExoPlayer

class MyCoursesFragment : Fragment() {

    private var _binding: FragmentMyCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CourseViewModel
    private lateinit var sessionManager: SessionManager
    private var exoPlayer: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = exoPlayer

        val database = AppDatabase.getInstance(requireContext())
        val repository = CourseRepository(database.courseDao())
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CourseViewModel::class.java]

        val adapter = CourseAdapter(
            courses = emptyList(),
            onEnrollClick = {},
            onItemClick = { courseUrl ->
                playVideo(courseUrl)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        val userId = sessionManager.getUserEmail()
        if (userId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "No user logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.setUserId(userId)
        viewModel.userCourses.observe(viewLifecycleOwner) { courses ->
            if (courses.isEmpty()) {
                Toast.makeText(requireContext(), "No courses found!", Toast.LENGTH_SHORT).show()
            } else {
                adapter.updateData(courses.map { it.courseUrl })
            }
        }
    }

    private fun playVideo(courseUrl: String) {
        binding.playerView.visibility = View.VISIBLE
        val mediaItem = MediaItem.fromUri(courseUrl)
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
        exoPlayer = null
        _binding = null
    }
}


