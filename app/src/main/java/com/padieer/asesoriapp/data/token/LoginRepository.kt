package com.padieer.asesoriapp.data.token

interface LoginRepository {
    suspend fun getToken(numControl: String, contrasena: String): String?
}