package mediscreen.patientInfo.service;

import java.util.List;

import mediscreen.patientInfo.exception.PatientInfoAlreadyExistException;
import mediscreen.patientInfo.model.PatientInfo;

public interface PatientInfoService {

	public PatientInfo addPatientInfo(PatientInfo toCreate) throws PatientInfoAlreadyExistException;

	public PatientInfo updatePatientInfo(PatientInfo toUpdate);

	public PatientInfo getPatientInfoById(long id);

	public List<PatientInfo> getPatientInfoByName(String family, String given);

	public List<PatientInfo> getAllPatientInfo();
}
