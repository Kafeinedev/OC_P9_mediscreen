package mediscreen.patientInfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mediscreen.patientInfo.model.PatientInfo;

/**
 * Repository for patient info data.
 */
@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {

	/**
	 * Find by family name.
	 *
	 * @param family the family name of the patient to search
	 * @return a PatientInfo of a patient possessing that name
	 */
	public Optional<PatientInfo> findByFamily(String family);
}
