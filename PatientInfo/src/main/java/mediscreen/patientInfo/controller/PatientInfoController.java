package mediscreen.patientInfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.patientInfo.model.PatientInfo;
import mediscreen.patientInfo.service.PatientInfoService;

@RestController
public class PatientInfoController {

	@Autowired
	private PatientInfoService patientInfoService;

	@PostMapping("/patient/add")
	@ResponseStatus(HttpStatus.CREATED)
	public PatientInfo addPatientInfo(@Valid PatientInfo patientInfo) {
		return patientInfoService.addPatientInfo(patientInfo);
	}

	@PutMapping("/patient/update")
	public PatientInfo updatePatientIndo(@Valid PatientInfo patientInfo) {
		return patientInfoService.updatePatientInfo(patientInfo);
	}

	@GetMapping("/patient/{id}")
	public PatientInfo getPatientInfoById(@RequestParam(name = "id") long id) {
		return patientInfoService.getPatientInfoById(id);
	}

	@GetMapping("/patient/search")
	public List<PatientInfo> getPatientInfoByName(@RequestParam(name = "family") String family,
			@RequestParam(name = "given") String given) {
		return patientInfoService.getPatientInfoByName(family, given);
	}

	@GetMapping("/patient/list")
	public List<PatientInfo> getAllPatientInfo() {
		return patientInfoService.getAllPatientInfo();
	}
}
