import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.domain.ProfessionalDomainEntity
import com.jhoglas.mysalon.domain.ScheduleDateDomainEntity
import com.jhoglas.mysalon.domain.ScheduleHourDomainEntity
import com.jhoglas.mysalon.domain.getEstablishments
import com.jhoglas.mysalon.domain.getProfessionals
import com.jhoglas.mysalon.domain.getScheduleDays
import com.jhoglas.mysalon.domain.getScheduleHours

class EstablishmentViewModel() : ViewModel() {

    var professionals by mutableStateOf(listOf<ProfessionalDomainEntity>())
        private set

    var scheduleDates by mutableStateOf((listOf<ScheduleDateDomainEntity>()))
        private set

    var scheduleHours by mutableStateOf((listOf<ScheduleHourDomainEntity>()))
        private set

    fun establishment(id: String) = getEstablishments().find { it.id == id }
    private fun listProfessionals(id: String? = null) {
        professionals = getProfessionals()
    }

    fun professionalUpdate(professional: ProfessionalDomainEntity) {
        val updatedProfessionals = professionals.map {
            it.copy(isSelected = it == professional)
        }
        scheduleHourClear()
        professionals = updatedProfessionals.toMutableList()
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

    init {
        listProfessionals()
    }
}
