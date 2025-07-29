package com.padieer.asesoriapp.ui.app

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
import com.padieer.asesoriapp.ui.nav.AppScreen


sealed class NavigationItem {

    data class Section(val title: String): NavigationItem()

    data class Item(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val badgeCount: Int? = null,
        val route: AppScreen
    ) : NavigationItem()

    data object LogOutItem: NavigationItem()
}

fun obtenAccionesDelEstudiante(rol: RolEstudiante): List<NavigationItem> {
    val acciones = arrayListOf<NavigationItem>()

    acciones.addAll(arrayOf(
        NavigationItem.Section("Usuario"),
        NavigationItem.Item(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = AppScreen.Usuario.HomeScreen,
        ),
        NavigationItem.Item(
            title = "Perfil",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            route = AppScreen.Usuario.PerfilScreen
        ),

        NavigationItem.Section("Asesorías"),
        NavigationItem.Item(
            title = "Pedir Asesoría",
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
            route = AppScreen.Asesoria.PedirAsesoriaScreen
        ),
        NavigationItem.Item(
            title = "Historial",
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
            route = AppScreen.Asesoria.HistorialAsesoriasScreen
        ),
    ))

    if (rol == RolEstudiante.ADMIN)
        acciones.add(NavigationItem.Item(
            // Solo disponible para admin
            title = "Asignar Asesor",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = AppScreen.Asesoria.AsignarAsesorScreen,
        ))

    if (rol == RolEstudiante.ASESOR || rol == RolEstudiante.ADMIN)
        acciones.addAll(arrayOf(
            NavigationItem.Section("Asesor"),
            NavigationItem.Item(
                title = "Asesorias Asignadas",
                selectedIcon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications,
                route = AppScreen.Asesor.AsesoriaAsesorScreen
            ),
            NavigationItem.Item(
                title = "Disponibilidad",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange,
                route = AppScreen.Asesor.DisponibilidadAsesorScreen
            ),
            NavigationItem.Item(
                title = "Historial",
                selectedIcon = Icons.AutoMirrored.Filled.List,
                unselectedIcon = Icons.AutoMirrored.Outlined.List,
                route = AppScreen.Asesor.HistorialAsesorScreen,
            ),
            NavigationItem.Item(
                title = "Estadísticas",
                selectedIcon = Icons.Filled.Star,
                unselectedIcon = Icons.Outlined.Star,
                route = AppScreen.Asesor.EstadisticasAsesorScreen
            )
        ))

    acciones.add(NavigationItem.LogOutItem)

    return acciones.toList()
}
