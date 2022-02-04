package mediscreen.patientInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mediscreen.patientInfo.model.PatientInfo;

@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {
	public List<PatientInfo> findByFamilyAndGiven(String family, String given);
}
