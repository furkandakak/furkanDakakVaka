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

        // Eğer kullanıcı zaten giriş yaptıysa, doğrudan HomeFragment'e yönlendir
        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            return
        }

        // BottomNavigationView'in görünürlüğünü güncelle
        (activity as MainActivity).manageBottomNavigationVisibility()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim() // Şifre alanı

            if (email.isEmpty()) {
                binding.etEmail.error = "Please enter a valid email"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            // Kullanıcı giriş bilgilerini kaydet
            sessionManager.saveLogin(email)

            // BottomNavigationView görünümünü güncelle
            (activity as MainActivity).manageBottomNavigationVisibility()

            // Ana sayfaya yönlendir
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
