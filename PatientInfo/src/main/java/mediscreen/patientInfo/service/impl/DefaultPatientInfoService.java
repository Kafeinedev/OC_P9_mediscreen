package mediscreen.patientInfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mediscreen.patientInfo.model.PatientInfo;
import mediscreen.patientInfo.repository.PatientInfoRepository;
import mediscreen.patientInfo.service.PatientInfoService;

@Service
public class DefaultPatientInfoService implements PatientInfoService {

	@Autowired
	private PatientInfoRepository patientInfoRepository;

	@Override
	public PatientInfo addPatientInfo(PatientInfo toCreate) {
		return null;
	}

	@Override
	public PatientInfo updatePatientInfo(PatientInfo toUpdate) {
		return null;
	}

	@Override
	public PatientInfo getPatientInfoById(long id) {
		return null;
	}

	@Override
	public List<PatientInfo> getPatientInfoByName(String family, String given) {
		return null;
	}

	@Override
	public List<PatientInfo> getAllPatientInfo() {
		return null;
	}
}
