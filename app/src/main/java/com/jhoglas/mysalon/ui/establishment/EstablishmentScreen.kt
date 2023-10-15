package com.jhoglas.mysalon.ui.establishment

import EstablishmentViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.network.GoogleAuthUiClient
import com.jhoglas.mysalon.ui.compoment.BannerComponent
import com.jhoglas.mysalon.ui.compoment.NavigationDrawerComponent
import com.jhoglas.mysalon.ui.compoment.ProfessionalsComponent
import com.jhoglas.mysalon.ui.compoment.ScheduleButtonComponent
import com.jhoglas.mysalon.ui.compoment.ScheduleDateComponent
import com.jhoglas.mysalon.ui.compoment.ScheduleHourComponent
import com.jhoglas.mysalon.ui.compoment.ServicesComponent
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.navigation.SystemBackButtonHandler
import com.jhoglas.mysalon.utils.extensions.getDayFromDate

@Composable
fun EstablishmentScreen(
    auth: GoogleAuthUiClient,
    establishmentViewModel: EstablishmentViewModel = viewModel(),
) {
    val establishmentId = AppRouter.bundle?.getString("establishmentId") ?: ""
    if (establishmentViewModel.establishment.id != establishmentId) {
        establishmentViewModel.establishment(establishmentId)
    }

    val establishmentServices = establishmentViewModel.establishmentService
    val establishment = establishmentViewModel.establishment
    var professionals = establishmentViewModel.professionals
    var scheduleDates = establishmentViewModel.scheduleDates
    var scheduleHours = establishmentViewModel.scheduleHours
    var scheduleBottomIsEnabled = establishmentViewModel.scheduleBottomIsEnabled

    NavigationDrawerComponent(
        screenName = R.string.establishment,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                establishment?.let {
                    BannerComponent(it)
                    Spacer(modifier = Modifier.height(8.dp))
                    ServicesComponent(establishmentServices) { service ->
                        establishmentViewModel.serviceUpdate(service)
                    }
                }
                professionals?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfessionalsComponent(it) { professional ->
                        establishmentViewModel.professionalUpdate(professional)
                        establishmentViewModel.scheduleDates(professional.id)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                ScheduleDateComponent(scheduleDates) { scheduleDate ->
                    establishmentViewModel.scheduleDateUpdate(scheduleDate)
                    establishmentViewModel.listScheduleHours(scheduleDate.date.getDayFromDate())
                }
                Spacer(modifier = Modifier.height(16.dp))
                ScheduleHourComponent(scheduleHours) { scheduleHour ->
                    establishmentViewModel.scheduleHourUpdate(scheduleHour)
                }
                Spacer(modifier = Modifier.weight(1f))
                ScheduleButtonComponent(scheduleBottomIsEnabled) {
                    establishmentViewModel.scheduleBottomClick()
                }
            }
        },
        auth = auth,
    )

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.HomeScreen)
    }
}
