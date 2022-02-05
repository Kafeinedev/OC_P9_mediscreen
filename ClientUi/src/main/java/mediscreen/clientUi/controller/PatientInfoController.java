package mediscreen.clientUi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import mediscreen.clientUi.bean.PatientInfo;
import mediscreen.clientUi.proxy.PatientInfoProxy;
import mediscreen.clientUi.util.APIErrorUtil;

@Controller
@Slf4j
public class PatientInfoController {

	private APIErrorUtil errorUtil = new APIErrorUtil();

	@Autowired
	private PatientInfoProxy patientInfoService;

	@GetMapping("/patient/list")
	public ModelAndView getAllPatients(Model model) {
		log.info("Get @ /patient/list");
		model.addAttribute("patientInfoList", patientInfoService.getAllPatientInfo());
		return new ModelAndView("patient/list", model.asMap());
	}

	@GetMapping("/patient/search/{id}")
	public ModelAndView getSinglePatient(@PathVariable long id, Model model) {
		log.info("Get @ /patient/search/" + id);
		model.addAttribute("patientInfoList", patientInfoService.getPatientInfoById(id));
		return new ModelAndView("patient/list", model.asMap());
	}

	@GetMapping("/patient/search")
	public ModelAndView getPatientInfo(@RequestParam String family, @RequestParam String given, Model model) {
		log.info("Get @ /patient/search family = " + family + " given = " + given);
		model.addAttribute("patientInfoList", patientInfoService.getPatientInfoByName(family, given));
		return new ModelAndView("patient/list", model.asMap());
	}

	@GetMapping("/patient/add")
	public ModelAndView getAddPatient(PatientInfo patientInfo, Model model) {
		log.info("Get @ /patient/add");
		model.addAttribute("patientInfo", patientInfo);
		return new ModelAndView("patient/add");
	}

	@PostMapping("/patient/add")
	public String postAddPatient(PatientInfo patientInfo, Model model, BindingResult result)
			throws JsonMappingException, JsonProcessingException {
		log.debug(patientInfo.toString());
		log.info("Post @ /patient/add patientInfo = " + patientInfo.toString());
		try {
			patientInfoService.addPatientInfo(patientInfo);
		} catch (FeignException e) {
			if (e.status() == 400) {// We only resolve bad request
				Map<String, String> errors = errorUtil.extractError(e.contentUTF8());
				errors.forEach((k, v) -> {
					result.addError(new FieldError("patientInfo", k, v));
				});
				return "patient/add";
			} else {
				throw e;
			}
		}

		return "redirect:/patient/list";
	}

	@GetMapping("/patient/update/{id}")
	public ModelAndView getUpdatePatient(@PathVariable(name = "id") long id, Model model) {
		log.info("Get @ /patient/update/" + id);
		model.addAttribute("patientInfo", patientInfoService.getPatientInfoById(id));

		return new ModelAndView("patient/update");
	}

	@PostMapping("/patient/update/{id}")
	public String putUpdatePatient(@PathVariable(name = "id") long id, PatientInfo patientInfo, Model model,
			BindingResult result) throws JsonMappingException, JsonProcessingException {
		log.debug(patientInfo.toString());
		log.info("Post @ /patient/update/" + id + " patientInfo = " + patientInfo.toString());
		patientInfo.setId(id);
		try {
			patientInfoService.updatePatientInfo(patientInfo);
		} catch (FeignException e) {
			if (e.status() == 400) {// We only resolve bad request
				Map<String, String> errors = errorUtil.extractError(e.contentUTF8());
				errors.forEach((k, v) -> {
					result.addError(new FieldError("patientInfo", k, v));
				});
				return "patient/update";
			} else {// Another method handle the rest.
				throw e;
			}
		}

		return "redirect:/patient/list";
	}
}
