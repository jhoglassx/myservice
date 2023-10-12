import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.domain.getEstablishments
import com.jhoglas.mysalon.domain.getProfessionals

class EstablishmentViewModel() : ViewModel() {

    fun establishment(id: String) = getEstablishments().find { it.id == id }
    fun listProfessionals(it: String) = getProfessionals()
}
