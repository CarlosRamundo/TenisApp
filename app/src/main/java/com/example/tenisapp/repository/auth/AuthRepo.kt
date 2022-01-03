package com.example.tenisapp.repository.auth

import com.google.firebase.auth.FirebaseUser


interface AuthRepo {
    suspend fun signIn(email: String, password: String): FirebaseUser?
}
