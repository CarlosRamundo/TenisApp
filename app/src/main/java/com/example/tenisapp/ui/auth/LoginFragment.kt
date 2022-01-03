package com.example.tenisapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tenisapp.R
import com.example.tenisapp.data.auth.AuthDataSource
import com.example.tenisapp.databinding.FragmentLoginBinding
import com.example.tenisapp.presentation.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var bindig: FragmentLoginBinding
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindig = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLoggin()
        goToRegisterPage()
    }

    private fun isUserLoggedIn() {
        auth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_selectPlayersFragment)
        }
    }

    private fun doLoggin() {
        bindig.btnSignin.setOnClickListener {
            val email = bindig.emailAdress.text.toString()
            val password = bindig.password.text.toString()
            validCredentials(email, password)
            signIn(email, password)
        }
    }

    private fun goToRegisterPage(){
        bindig.txtSignup.setOnClickListener(){
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun validCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            bindig.emailAdress.error = "Email is empty"
            return
        }
        if (password.isEmpty()) {
            bindig.password.error = "Password is empty"
            return
        }

    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    bindig.progressBar.visibility = View.VISIBLE
                    bindig.btnSignin.isEnabled = false
                }
                is Result.Success -> {
                    bindig.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }
                is Result.Failure -> {
                    bindig.progressBar.visibility = View.GONE
                    bindig.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error:${result.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
}