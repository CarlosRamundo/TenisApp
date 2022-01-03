package com.example.tenisapp.repository.auth

import com.example.tenisapp.data.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)

}