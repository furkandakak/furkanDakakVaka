package com.furkan.dakak.furkandakakvaka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.furkan.dakak.furkandakakvaka.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            return
        }

        (activity as MainActivity).manageBottomNavigationVisibility()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Please enter a valid email"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            sessionManager.saveLogin(email)

            (activity as MainActivity).manageBottomNavigationVisibility()

            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
