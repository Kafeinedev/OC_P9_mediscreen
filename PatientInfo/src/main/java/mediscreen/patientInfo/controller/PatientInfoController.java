package mediscreen.patientInfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mediscreen.patientInfo.exception.PatientInfoAlreadyExistException;
import mediscreen.patientInfo.model.PatientInfo;
import mediscreen.patientInfo.service.PatientInfoService;

/*
 * CRUD controller for patientInfo
 * 
 */
@RestController
@Slf4j
public class PatientInfoController {

	@Autowired
	private PatientInfoService patientInfoService;

	/**
	 * Adds the patient info.
	 *
	 * @param patientInfo the patient info to add
	 * @return the created patient info
	 * @throws PatientInfoAlreadyExistException if the patientinfo given posses an
	 *                                          id that already exist
	 */
	@PostMapping("/patient/add")
	@ResponseStatus(HttpStatus.CREATED)
	public PatientInfo addPatientInfo(@Valid PatientInfo patientInfo) throws PatientInfoAlreadyExistException {
		log.info("Post @ /patient/add : " + patientInfo.toString());
		return patientInfoService.addPatientInfo(patientInfo);
	}

	/**
	 * Update patient info.
	 *
	 * @param patientInfo the patient info to update
	 * @return the updated patient info
	 */
	@PutMapping("/patient/update")
	public PatientInfo updatePatientInfo(@Valid PatientInfo patientInfo) {
		log.info("Put @ /patient/update : " + patientInfo.toString());
		return patientInfoService.updatePatientInfo(patientInfo);
	}

	/**
	 * Gets the patient info by id.
	 *
	 * @param id the id of the patientinfo to find
	 * @return the patient info associated with the given id
	 */
	@GetMapping("/patient/search/{id}")
	public PatientInfo getPatientInfoById(@PathVariable(name = "id") long id) {
		log.info("Get @ /patient/search/" + id);
		return patientInfoService.getPatientInfoById(id);
	}

	/**
	 * Gets the patient info by name.
	 *
	 * @param family the family name of the patient to find
	 * @return a patient possessing that name.
	 */
	@GetMapping("/patient/search")
	public PatientInfo getPatientInfoByName(@RequestParam(name = "family") String family) {
		log.info("Get @ /patient/search family = " + family);
		return patientInfoService.getPatientInfoByName(family);
	}

	/**
	 * Gets the all patient info.
	 *
	 * @return a list with all the patient info
	 */
	@GetMapping("/patient/list")
	public List<PatientInfo> getAllPatientInfo() {
		log.info("Get @ /patient/list");
		return patientInfoService.getAllPatientInfo();
	}
}
