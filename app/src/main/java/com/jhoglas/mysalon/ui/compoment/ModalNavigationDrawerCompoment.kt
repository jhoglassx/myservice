package com.jhoglas.mysalon.ui.compoment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.auth.AuthClient
import com.jhoglas.mysalon.ui.auth.LoginViewModel
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.home.HomeViewModel
import com.jhoglas.mysalon.ui.navigation.AppRouter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerComponent(
    homeViewModel: HomeViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    auth: AuthClient,
    screenName: Int,
    content: @Composable () -> Unit,
) {
    loginViewModel.getSignedInUser()
    val navigationDrawerItems = homeViewModel.navigationItems
    val userDataState by loginViewModel.userDataState

    val scaffoldState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    ModalNavigationDrawer(
        drawerState = scaffoldState,
        drawerContent = {
            ModalDrawerSheet {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        coroutineScope.launch {
                            loginViewModel.loadingScreen(State.LOADING)
                            auth.signOut()
                            loginViewModel.loadingScreen(State.SUCCESS)
                        }
                    },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            if (userDataState.image.isNullOrEmpty()) {
                                Icon(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp),
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Profile Image"
                                )
                            } else {
                                AsyncImage(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp),
                                    model = userDataState.image ?: Icons.Filled.Person,
                                    contentDescription = "Profile Image ${userDataState.name}"
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = userDataState.name ?: "",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Text(
                                text = userDataState.email ?: "",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                navigationDrawerItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            AppRouter.navigateTo(item.screen)
                            Log.d("Coming Here", "onClick ${item.itemId}")
                            selectedItemIndex = index
                            coroutineScope.launch {
                                scaffoldState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        coroutineScope.launch {
                            loginViewModel.loadingScreen(State.LOADING)
                            auth.signOut()
                            loginViewModel.loadingScreen(State.SUCCESS)
                        }
                    },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.logout),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    AppToolBar(
                        toolbarTitle = stringResource(id = screenName),
                        logoutButtonClicked = {
                            coroutineScope.launch {
                                loginViewModel.loadingScreen(State.LOADING)
                                auth.signOut()
                                loginViewModel.loadingScreen(State.SUCCESS)
                            }
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
                    content()
                }
            }
        }
    )
}
