package com.jhoglas.mysalon.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.compoment.EstablishmentListComponent
import com.jhoglas.mysalon.ui.compoment.NavigationDrawerComponent
import com.jhoglas.mysalon.ui.compoment.PromotionsComponent

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
) {
    NavigationDrawerComponent(
        screenName = R.string.home,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
            PromotionsComponent()
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
            EstablishmentListComponent(homeViewModel.listEstablishment())
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
