
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.domain.EstablishmentDomainEntity
import com.jhoglas.mysalon.domain.ProfessionalDomainEntity
import com.jhoglas.mysalon.domain.ScheduleDateDomainEntity
import com.jhoglas.mysalon.domain.ScheduleEntity
import com.jhoglas.mysalon.domain.ScheduleHourDomainEntity
import com.jhoglas.mysalon.domain.ServiceDomainEntity
import com.jhoglas.mysalon.domain.getEstablishments
import com.jhoglas.mysalon.domain.getProfessionals
import com.jhoglas.mysalon.domain.getScheduleDays
import com.jhoglas.mysalon.domain.getScheduleHours
import java.time.LocalTime
import java.util.Date

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

    var schedule: ScheduleEntity by mutableStateOf(ScheduleEntity())
        private set

    var scheduleBottomIsEnabled: Boolean by mutableStateOf(false)
        private set

    fun establishment(establishmentId: String) {
        getEstablishments().find { it.id == establishmentId }?.let {
            establishment = it
            establishmentService = it.services.toMutableList()
            scheduleHourClear()
            scheduleDateClear()
        }
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
        scheduleBottomIsEnabled = false

        scheduleHourClear()
        scheduleDateClear()
    }

    private fun listProfessionals(
        establishmentId: String? = null,
    ) = getProfessionals().filter { it.establishmentId == establishmentId }

    fun professionalUpdate(professional: ProfessionalDomainEntity) {
        val updatedProfessionals = professionals.map {
            it.copy(isSelected = it == professional)
        }
        scheduleHourClear()
        scheduleDateClear()
        professionals = updatedProfessionals
    }

    private fun professionalFilter() {
        val selectedServices = establishmentService.filter { it.isSelected }.map { it.title }

        val professionalsFilter = listProfessionals(establishmentId = establishment.id)
            .filter { professional ->
                selectedServices.all { selectedService ->
                    professional.services.any { it.title == selectedService }
                }
            }

        professionals = if (selectedServices.isEmpty()) emptyList() else professionalsFilter
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
        scheduleBottomIsEnabled = scheduleHours.any { it.isSelected }
    }

    private fun scheduleHourClear() {
        scheduleHours = emptyList()
    }

    private fun scheduleDateClear() {
        scheduleDates = emptyList()
    }

    fun scheduleBottomClick() {
        schedule = ScheduleEntity(
            userId = "1",
            establishmentId = establishment.id,
            professionalId = professionals.find { it.isSelected }?.id ?: "",
            service = establishmentService.filter { it.isSelected },
            day = scheduleDates.find { it.isSelected }?.date ?: Date(),
            time = scheduleHours.find { it.isSelected }?.start ?: LocalTime.MIN,
            isAvailable = true
        )
    }
}
