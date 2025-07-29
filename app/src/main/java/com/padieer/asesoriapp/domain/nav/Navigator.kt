package com.padieer.asesoriapp.domain.nav

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.padieer.asesoriapp.ui.nav.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class Navigator {

    private val _channel = Channel<Action>()
    val channel = _channel.receiveAsFlow()

    suspend fun emit(action: Action) {
        _channel.send(action)
    }

    fun consumeAction(action: Action, navController: NavController?, context: Context) {
        when (action) {
            is Action.GoTo -> {
                navController?.navigate(action.screen)
            }
            is Action.GoToInclusive -> {
                navController?.navigate(action.screen) {
                    popUpTo (route = action.upTo) { inclusive = true }
                }
            }
            is Action.Toast -> {
                Toast.makeText(context, action.message, Toast.LENGTH_LONG).show()
            }
            Action.PopBackStack -> {
                navController?.popBackStack()
            }
            Action.Up -> {
                navController?.navigateUp()
            }
        }
    }

    sealed class Action {
        data class GoTo(val screen: Screen): Action()
        data class GoToInclusive(val screen: Screen, val upTo: Screen): Action()
        data object PopBackStack: Action()
        data object Up: Action()
        data class Toast(val message: String): Action()
    }
}