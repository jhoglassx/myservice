
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.domain.EstablishmentDomainEntity
import com.jhoglas.mysalon.domain.ProfessionalDomainEntity
import com.jhoglas.mysalon.domain.ScheduleDateDomainEntity
import com.jhoglas.mysalon.domain.ScheduleHourDomainEntity
import com.jhoglas.mysalon.domain.ServiceDomainEntity
import com.jhoglas.mysalon.domain.getEstablishments
import com.jhoglas.mysalon.domain.getProfessionals
import com.jhoglas.mysalon.domain.getScheduleDays
import com.jhoglas.mysalon.domain.getScheduleHours

class EstablishmentViewModel() : ViewModel() {

    var establishment by mutableStateOf(EstablishmentDomainEntity())
        private set

    var establishmentService by mutableStateOf(listOf<ServiceDomainEntity>())
        private set

    var professionals by mutableStateOf(listOf<ProfessionalDomainEntity>())
        private set

    var scheduleDates by mutableStateOf(listOf<ScheduleDateDomainEntity>())
        private set

    var scheduleHours by mutableStateOf(listOf<ScheduleHourDomainEntity>())
        private set

    fun establishment(establishmentId: String) {
        getEstablishments().find { it.id == establishmentId }?.let {
            establishment = it
            establishmentService = it.services.toMutableList()
            professionals = listProfessionals(it.id)
        }
    }

    private fun listProfessionals(
        establishmentId: String? = null,
    ) = getProfessionals().filter { it.establishmentId == establishmentId }

    fun professionalUpdate(professional: ProfessionalDomainEntity) {
        val updatedProfessionals = professionals.map {
            it.copy(isSelected = it == professional)
        }
        scheduleHourClear()
        professionals = updatedProfessionals
    }

    private fun professionalFilter() {
        val serviceSelected = establishmentService.filter { it.isSelected }
        val professionalsFilter = listProfessionals(establishmentId = establishment.id).filter { professional ->
            serviceSelected.any { selectedService ->
                professional.services.filter { it.title == selectedService.title }.any { true }
            }
        }
        professionals = professionalsFilter.ifEmpty { listProfessionals(establishmentId = establishment.id) }
    }

    fun scheduleDates(professionalId: String) {
        scheduleDates = getScheduleDays().filter {
            it.professionalId == professionalId
        }
    }

    fun scheduleDateUpdate(date: ScheduleDateDomainEntity) {
        val updatedDates = scheduleDates.map {
            it.copy(isSelected = it == date)
        }
        scheduleDates = updatedDates
    }

    fun listScheduleHours(day: Int) {
        scheduleHours = getScheduleHours().filter { it.day == day }
    }

    fun scheduleHourUpdate(hour: ScheduleHourDomainEntity) {
        val updatedHours = scheduleHours.map {
            it.copy(isSelected = it == hour)
        }
        scheduleHours = updatedHours
    }

    private fun scheduleHourClear() {
        scheduleHours = emptyList()
    }

    fun serviceUpdate(service: ServiceDomainEntity) {
        val updatedService = establishmentService.map {
            if (it == service) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        establishmentService = updatedService
        professionalFilter()
    }
}
