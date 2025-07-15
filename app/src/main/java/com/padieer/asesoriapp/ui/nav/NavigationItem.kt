package com.padieer.asesoriapp.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavigationItem {

    data class Section(val title: String): NavigationItem()

    data class Item(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val badgeCount: Int? = null,
        val route: Screen
    ) : NavigationItem()
}

val navigationItems = arrayOf(
    NavigationItem.Section("Usuario"),
    NavigationItem.Item(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Screen.App.PerfilScreen,
    ),
    NavigationItem.Item(
        title = "Perfil",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        route = Screen.App.PerfilScreen
    ),

    NavigationItem.Section("Asesorías"),
    NavigationItem.Item(
        title = "Pedir Asesoría",
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add,
        route = Screen.App.PerfilScreen
    ),
    NavigationItem.Item(
        title = "Historial",
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List,
        route = Screen.App.PerfilScreen
    ),
    NavigationItem.Item( // Solo disponible para admin
        title = "Asignar Asesor",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = Screen.App.PerfilScreen,
    ),

    NavigationItem.Section("Asesor"),
    NavigationItem.Item(
        title = "Asesorias Asignadas",
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
        route = Screen.App.PerfilScreen
    ),
    NavigationItem.Item(
        title = "Disponibilidad",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
        route = Screen.App.PerfilScreen
    ),
    NavigationItem.Item(
        title = "Historial",
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List,
        route = Screen.App.PerfilScreen,
    ),
    NavigationItem.Item(
        title = "Estadísticas",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star,
        route = Screen.App.PerfilScreen
    )
)
