package com.furkan.dakak.furkandakakvaka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkan.dakak.furkandakakvaka.databinding.FragmentHomeBinding
import com.furkan.dakak.furkandakakvaka.db.AppDatabase
import com.furkan.dakak.furkandakakvaka.db.Course
import com.furkan.dakak.furkandakakvaka.mvvm.CourseRepository
import com.furkan.dakak.furkandakakvaka.mvvm.CourseViewModel
import com.furkan.dakak.furkandakakvaka.mvvm.ViewModelFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var exoPlayer: ExoPlayer? = null
    private lateinit var viewModel: CourseViewModel
    private lateinit var sessionManager: SessionManager

    private val courseUrls = listOf(
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        val database = AppDatabase.getInstance(requireContext())
        val repository = CourseRepository(database.courseDao())
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CourseViewModel::class.java]

        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = exoPlayer

        val adapter = CourseAdapter(courseUrls, onEnrollClick = { courseUrl ->
            val userId = sessionManager.getUserEmail() ?: return@CourseAdapter
            val course = Course(userId = userId, courseUrl = courseUrl)
            viewModel.addCourse(course)
            Toast.makeText(requireContext(), "Course enrolled!", Toast.LENGTH_SHORT).show()
        }, onItemClick = { courseUrl ->
            playVideo(courseUrl)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun playVideo(videoUrl: String) {
        binding.playerView.visibility = View.VISIBLE

        val mediaItem = MediaItem.fromUri(videoUrl)
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
