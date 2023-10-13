import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.domain.getEstablishments
import com.jhoglas.mysalon.domain.getProfessionals
import com.jhoglas.mysalon.domain.getScheduleDays
import com.jhoglas.mysalon.domain.getScheduleHours

class EstablishmentViewModel() : ViewModel() {

    fun establishment(id: String) = getEstablishments().find { it.id == id }
    fun listProfessionals(id: String) = getProfessionals()
    fun listScheduleDays(professionalId: String) = getScheduleDays().filter { it.professionalId == professionalId }
    fun listScheduleHours(day: Int) = getScheduleHours().filter { it.day == day }
}
