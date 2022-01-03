package com.example.tenisapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tenisapp.R
import com.example.tenisapp.data.auth.AuthDataSource
import com.example.tenisapp.databinding.FragmentLoginBinding
import com.example.tenisapp.presentation.auth.AuthViewModel
import com.example.tenisapp.presentation.auth.AuthViewModelFactory
import com.example.tenisapp.repository.auth.AuthRepoImpl
import com.example.tenisapp.core.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth : FirebaseAuth
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImpl(AuthDataSource()))
    }
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        auth = Firebase.auth
        isUserLoggedIn()
        doLogin()
        createdUser()
    }

    private fun doLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.signIn(email, password).observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        findNavController().navigate(R.id.action_loginFragment_to_selectPlayersFragment)
                    }
                    is Result.Failure-> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Falló el ingreso a la App, intente nuevamente!!", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    private fun isUserLoggedIn() {
        auth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_selectPlayersFragment)
        }
    }
    private fun createdUser() {
        binding.createdUser.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrerFragment2)
        }
    }
}
