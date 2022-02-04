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

@RestController
@Slf4j
public class PatientInfoController {

	@Autowired
	private PatientInfoService patientInfoService;

	@PostMapping("/patient/add")
	@ResponseStatus(HttpStatus.CREATED)
	public PatientInfo addPatientInfo(@Valid PatientInfo patientInfo) throws PatientInfoAlreadyExistException {
		log.info("Post @ /patient/add : " + patientInfo.toString());
		return patientInfoService.addPatientInfo(patientInfo);
	}

	@PutMapping("/patient/update")
	public PatientInfo updatePatientInfo(@Valid PatientInfo patientInfo) {
		log.info("Put @ /patient/update : " + patientInfo.toString());
		return patientInfoService.updatePatientInfo(patientInfo);
	}

	@GetMapping("/patient/search/{id}")
	public PatientInfo getPatientInfoById(@PathVariable(name = "id") long id) {
		log.info("Get @ /patient/search/" + id);
		return patientInfoService.getPatientInfoById(id);
	}

	@GetMapping("/patient/search")
	public List<PatientInfo> getPatientInfoByName(@RequestParam(name = "family") String family,
			@RequestParam(name = "given") String given) {
		log.info("Get @ /patient/search family = " + family + " given = " + given);
		return patientInfoService.getPatientInfoByName(family, given);
	}

	@GetMapping("/patient/list")
	public List<PatientInfo> getAllPatientInfo() {
		log.info("Get @ /patient/list");
		return patientInfoService.getAllPatientInfo();
	}
}
