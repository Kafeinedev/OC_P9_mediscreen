package mediscreen.clientUi.controller;

import java.util.List;
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

/**
 * Controller for CRUD operation on patientInfo.
 */
@Controller
@Slf4j
public class PatientInfoController {

	private APIErrorUtil errorUtil = new APIErrorUtil();

	@Autowired
	private PatientInfoProxy patientInfoService;

	/**
	 * Gets the all patients.
	 *
	 * @param model the model
	 * @return a view with a list of all patients in model
	 */
	@GetMapping("/patient/list")
	public ModelAndView getAllPatients(Model model) {
		log.info("Get @ /patient/list");
		model.addAttribute("patientInfoList", patientInfoService.getAllPatientInfo());
		return new ModelAndView("patient/list", model.asMap());
	}

	/**
	 * Gets a single patient.
	 *
	 * @param id    the id of the patient to get
	 * @param model the model
	 * @return a view with a single patients in model
	 */
	@GetMapping("/patient/search/{id}")
	public ModelAndView getSinglePatient(@PathVariable long id, Model model) {
		log.info("Get @ /patient/search/" + id);
		model.addAttribute("patientInfoList", patientInfoService.getPatientInfoById(id));
		return new ModelAndView("patient/list", model.asMap());
	}

	/**
	 * Search for patient via their name.
	 *
	 * @param family the family name
	 * @param given  the given name
	 * @param model  the model
	 * @return a view with a list of all patients that possess both names.
	 */
	@GetMapping("/patient/search")
	public ModelAndView getPatientInfo(@RequestParam String family, Model model) {
		log.info("Get @ /patient/search family = " + family);
		model.addAttribute("patientInfoList", List.of(patientInfoService.getPatientInfoByName(family)));
		return new ModelAndView("patient/list", model.asMap());
	}

	/**
	 * Form for adding patient
	 *
	 * @param patientInfo the patient info
	 * @param model       the model
	 * @return a view with a form to add a patient
	 */
	@GetMapping("/patient/add")
	public ModelAndView getAddPatient(PatientInfo patientInfo, Model model) {
		log.info("Get @ /patient/add");
		model.addAttribute("patientInfo", patientInfo);
		return new ModelAndView("patient/add");
	}

	/**
	 * Post add patient.
	 *
	 * @param patientInfo the patient info to add
	 * @param model       the model
	 * @param result      the result
	 * @return a view with a list of all patient info
	 * @throws JsonMappingException    the json mapping exception
	 * @throws JsonProcessingException the json processing exception
	 */
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

	/**
	 * Form for updating patient.
	 *
	 * @param id    the id of the patientinfo to update
	 * @param model the model
	 * @return a view with a form to update patientinfo in model
	 */
	@GetMapping("/patient/update/{id}")
	public ModelAndView getUpdatePatient(@PathVariable(name = "id") long id, Model model) {
		log.info("Get @ /patient/update/" + id);
		model.addAttribute("patientInfo", patientInfoService.getPatientInfoById(id));

		return new ModelAndView("patient/update");
	}

	/**
	 * Put update patient.
	 *
	 * @param id          the id of the patient to update
	 * @param patientInfo the patient info
	 * @param model       the model
	 * @param result      the result
	 * @return a view with a list of all patient
	 * @throws JsonMappingException    the json mapping exception
	 * @throws JsonProcessingException the json processing exception
	 */
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
