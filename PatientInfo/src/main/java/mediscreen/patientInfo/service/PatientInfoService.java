package mediscreen.patientInfo.service;

import java.util.List;

import mediscreen.patientInfo.exception.PatientInfoAlreadyExistException;
import mediscreen.patientInfo.model.PatientInfo;

/**
 * Crud interface for PatientInfoService.
 */
public interface PatientInfoService {

	/**
	 * Add a new patient info.
	 *
	 * @param toCreate the patientInfo to create
	 * @return the patient info created
	 * @throws PatientInfoAlreadyExistException if the patientInfo param already is
	 *                                          in the database
	 */
	public PatientInfo addPatientInfo(PatientInfo toCreate) throws PatientInfoAlreadyExistException;

	/**
	 * Update an existing patient info.
	 *
	 * @param toUpdate the patientInfo to update
	 * @return the updated patient info
	 */
	public PatientInfo updatePatientInfo(PatientInfo toUpdate);

	/**
	 * Get a patient info by id.
	 *
	 * @param id the id of the patientInfo to find
	 * @return the patientInfo associated with that id
	 */
	public PatientInfo getPatientInfoById(long id);

	/**
	 * Gets the patient info by name.
	 *
	 * @param family the family name of the patient to find
	 * @param given  the given name of the patient to find
	 * @return a PatientInfo with a patient possessing that name
	 */
	public PatientInfo getPatientInfoByName(String family);

	/**
	 * Gets the all patient info.
	 *
	 * @return the all patient info in database
	 */
	public List<PatientInfo> getAllPatientInfo();
}
