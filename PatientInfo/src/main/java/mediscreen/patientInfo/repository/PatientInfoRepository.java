package mediscreen.patientInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mediscreen.patientInfo.model.PatientInfo;

/**
 * Repository for patient info data.
 */
@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {

	/**
	 * Find by family name and given name.
	 *
	 * @param family the family name of the patient to search
	 * @param given  the given name of the patient to search
	 * @return the list of patient possessing both the family and given name
	 */
	public List<PatientInfo> findByFamilyAndGiven(String family, String given);
}
