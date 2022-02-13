package mediscreen.assessment.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import mediscreen.assessment.beans.PatientInfo;

/*
 * Proxy for the patientInfo microservice
 * 
 */
@FeignClient(name = "patientInfo", url = "${url.patientinfo}")
public interface PatientInfoProxy {
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
	public List<PatientInfo> getPatientInfoByName(@RequestParam(name = "family") String family);
}