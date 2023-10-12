package com.jhoglas.mysalon.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.compoment.AppToolBar
import com.jhoglas.mysalon.ui.compoment.EstablishmentListComponent
import com.jhoglas.mysalon.ui.compoment.NavigationDrawer
import com.jhoglas.mysalon.ui.compoment.PromotionsComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
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
                            // homeViewModel.logout()
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
                        Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
                        PromotionsComponent()
                        Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
                        EstablishmentListComponent()
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
