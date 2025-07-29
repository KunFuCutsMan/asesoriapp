package com.padieer.asesoriapp.ui.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.padieer.asesoriapp.App
import com.padieer.asesoriapp.di.FakeAppModule
import com.padieer.asesoriapp.ui.common.FullScreenLoading
import com.padieer.asesoriapp.ui.nav.graph.AppGraph
import com.padieer.asesoriapp.ui.theme.AsesoriAppTheme
import kotlinx.coroutines.launch

@Composable
fun ApplicationNavigationDrawer(rootNavController: NavController? = null) {
    val viewModel: AppViewModel = viewModel( factory = AppViewModel.Factory() )
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.navigator.channel.collect {
            viewModel.navigator.consumeAction(it, rootNavController, context)
        }
    }

    ApplicationNavigationDrawer(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationNavigationDrawer(viewModel: AppViewModel) {

    val drawerState = rememberDrawerState( initialValue = DrawerValue.Closed )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(1) }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        FullScreenLoading()
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(28.dp))
                obtenAccionesDelEstudiante(uiState.rolEstudiante).forEachIndexed { index, item ->
                    when (item) {
                        is NavigationItem.Item -> {
                            NavItem(
                                item = item,
                                isSelected = index == selectedItemIndex,
                                onClick = {
                                    navController.navigate(item.route)
                                    selectedItemIndex = index
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                        is NavigationItem.Section -> {
                            NavSection(
                                item = item,
                                index = index,
                            )
                        }
                        is NavigationItem.LogOutItem -> {
                            Spacer(Modifier.weight(1f))
                            NavLogOut(
                                modifier = Modifier.padding(vertical = 28.dp),
                                onClick = { viewModel.onEvent(AppEvent.LogOutClick) }
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Padieer") },
                    navigationIcon = {
                        IconButton( onClick = {
                            scope.launch { drawerState.open() }
                        } ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
            ) {
                AppGraph(navController)
            }
        }

    }
}

@Composable
fun NavItem(modifier: Modifier = Modifier, item: NavigationItem.Item, isSelected: Boolean, onClick : () -> Unit = {}) {
    NavigationDrawerItem(
        modifier = modifier.padding( horizontal = 16.dp ),
        label = { Text(item.title) },
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = if (isSelected) {
                    item.selectedIcon
                } else item.unselectedIcon,
                contentDescription = item.title
            )
        },
        badge = {
            item.badgeCount?.let { Text(item.badgeCount.toString()) }
        }
    )
}

@Composable
fun NavSection(modifier: Modifier = Modifier, item: NavigationItem.Section, index: Int) {
    Column(
        modifier = modifier.padding(horizontal = 28.dp)
    ) {
        if ( index != 0 ) HorizontalDivider(modifier = Modifier.padding( vertical = 16.dp ))
        Text(text = item.title, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun NavLogOut(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    NavigationDrawerItem(
        modifier = modifier.padding(horizontal = 16.dp),
        label = { Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error) },
        icon = { Icon(imageVector = Icons.Outlined.Close, contentDescription = "Cerrar Sesión", tint = MaterialTheme.colorScheme.error) },
        selected = false,
        onClick = onClick,
    )
}

@Preview
@Composable
fun ApplicationScaffoldPreview() {
    App.appModule = FakeAppModule()
    AsesoriAppTheme {
        ApplicationNavigationDrawer()
    }
}