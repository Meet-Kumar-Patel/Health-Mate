package com.example.project13application.firebase

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

object Auth {

    private val TAG = "AuthManager"
    private val auth = FirebaseAuth.getInstance()

    /**
     * Signs up with callbacks so callers can handle success or failure accordingly
     */
    fun signUp(email: String, password: String, onSuccess: () -> AuthResult, onFailure: (Exception) -> Exception) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it)
                Log.e(TAG, "Error signing up user: ${it.message}")
            }
    }

    /**
     * Signs in user with callbacks so callers can handle success or failure accordingly
     */
    fun signIn(email: String, password: String, onSuccess: () -> AuthResult, onFailure: (Exception) -> Exception) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it)
                Log.e(TAG, "Error signing in user: ${it.message}")
            }
    }

    fun signOut() {
        auth.signOut()
    }
}