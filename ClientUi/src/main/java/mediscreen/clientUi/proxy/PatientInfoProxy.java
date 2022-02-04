package mediscreen.clientUi.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import mediscreen.clientUi.bean.PatientInfo;

@FeignClient(name = "patientInfo", url = "${url.patientinfo}")
public interface PatientInfoProxy {

	@PostMapping(value = "/patient/add", consumes = "application/x-www-form-urlencoded")
	public PatientInfo addPatientInfo(PatientInfo patientInfo);

	@PutMapping(value = "/patient/update", consumes = "application/x-www-form-urlencoded")
	public PatientInfo updatePatientInfo(PatientInfo patientInfo);

	@GetMapping("/patient/search/{id}")
	public PatientInfo getPatientInfoById(@PathVariable(name = "id") long id);

	@GetMapping("/patient/search")
	public List<PatientInfo> getPatientInfoByName(@RequestParam(name = "family") String family,
			@RequestParam(name = "given") String given);

	@GetMapping("/patient/list")
	public List<PatientInfo> getAllPatientInfo();
}
