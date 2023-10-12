package com.jhoglas.mysalon.ui.establishment

import EstablishmentViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.compoment.AppToolBar
import com.jhoglas.mysalon.ui.compoment.BannerComponent
import com.jhoglas.mysalon.ui.compoment.NavigationDrawer
import com.jhoglas.mysalon.ui.compoment.ProfessionalListComponent
import com.jhoglas.mysalon.ui.home.HomeViewModel
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.navigation.SystemBackButtonHandler
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EstablishmentScreen(
    homeViewModel: HomeViewModel = viewModel(),
    establishmentViewModel: EstablishmentViewModel = viewModel(),
) {
    val establishmentId = AppRouter.bundle?.getString("establishmentId")
    val establishment = establishmentId?.let { establishmentViewModel.establishment(it) }
    val professionals = establishmentId?.let { establishmentViewModel.listProfessionals(it) }
    val scaffoldState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = scaffoldState,
        drawerContent = {
            NavigationDrawer(
                value = "",
                navigationItem = listOf(),
                onNavigationItemClicked = {
                    Log.d("Coming Here", "onNavigationItemClicked ${it.itemId}")
                }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    AppToolBar(
                        toolbarTitle = stringResource(id = R.string.home),
                        logoutButtonClicked = {
                            homeViewModel.logout()
                        },
                        navigationIconClicked = {
                            coroutineScope.launch {
                                scaffoldState.open()
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        establishment?.let {
                            BannerComponent(it)
                        }
                        professionals?.let {
                            ProfessionalListComponent(it)
                        }
                    }
                }
            }
        }
    )

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.HomeScreen)
    }
}
