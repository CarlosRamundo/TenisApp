package com.example.tenisapp.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthDataSource {
    private lateinit var auth : FirebaseAuth
    suspend fun signIn(email:String, password:String): FirebaseUser?{
        val authResult= FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }
}