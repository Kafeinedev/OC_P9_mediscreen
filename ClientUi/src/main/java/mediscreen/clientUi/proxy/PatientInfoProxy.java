package mediscreen.clientUi.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import mediscreen.clientUi.bean.PatientInfo;

/*
 * Proxy for the patientInfo microservice
 * 
 */
@FeignClient(name = "patientInfo", url = "${url.patientinfo}")
public interface PatientInfoProxy {

	/**
	 * Adds the patient info.
	 *
	 * @param patientInfo the patient info to add
	 * @return the created patient info
	 */
	@PostMapping(value = "/patient/add", consumes = "application/x-www-form-urlencoded")
	public PatientInfo addPatientInfo(PatientInfo patientInfo);

	/**
	 * Update patient info.
	 *
	 * @param patientInfo the patient info to update
	 * @return the updated patient info
	 */
	@PutMapping(value = "/patient/update", consumes = "application/x-www-form-urlencoded")
	public PatientInfo updatePatientInfo(PatientInfo patientInfo);

	/**
	 * Gets the patient info by id.
	 *
	 * @param id the id of the patientinfo to find
	 * @return the patient info associated with the given id
	 */
	@GetMapping("/patient/search/{id}")
	public PatientInfo getPatientInfoById(@PathVariable(name = "id") long id);

	/**
	 * Gets the patient info by name.
	 *
	 * @param family the family name of the patient to find
	 * @param given  the given name of the patient to find
	 * @return a list of patient possessing both family and given name.
	 */
	@GetMapping("/patient/search")
	public List<PatientInfo> getPatientInfoByName(@RequestParam(name = "family") String family,
			@RequestParam(name = "given") String given);

	/**
	 * Gets the all patient info.
	 *
	 * @return a list with all the patient info
	 */
	@GetMapping("/patient/list")
	public List<PatientInfo> getAllPatientInfo();
}
