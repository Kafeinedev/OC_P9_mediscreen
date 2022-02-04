package mediscreen.clientUi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import mediscreen.clientUi.bean.PatientInfo;
import mediscreen.clientUi.proxy.PatientInfoProxy;

@Controller
@Slf4j
public class PatientInfoController {

	@Autowired
	private PatientInfoProxy patientInfoService;

	@GetMapping("/patient/list")
	public ModelAndView getAllPatients(Model model) {
		model.addAttribute("infoList", patientInfoService.getAllPatientInfo());
		return new ModelAndView("patient/list", model.asMap());
	}

	@GetMapping("/patient/search/{id}")
	public ModelAndView getSinglePatient(@PathVariable long id, Model model) {
		model.addAttribute("infoList", patientInfoService.getPatientInfoById(id));
		return new ModelAndView("patient/list", model.asMap());
	}

	@GetMapping("/patient/search")
	public ModelAndView getPatientInfo(@RequestParam String family, @RequestParam String given, Model model) {
		model.addAttribute("infoList", patientInfoService.getPatientInfoByName(family, given));
		return new ModelAndView("patient/list", model.asMap());
	}

	@GetMapping("/patient/add")
	public ModelAndView getAddPatient(PatientInfo info, Model model) {
		model.addAttribute("info", info);
		return new ModelAndView("patient/add");
	}

	@PostMapping("/patient/add")
	public ModelAndView postAddPatient(PatientInfo info, Model model) {
		log.debug(info.toString());
		patientInfoService.addPatientInfo(info);

		return new ModelAndView("redirect:/patient/list");
	}

	@GetMapping("/patient/update/{id}")
	public ModelAndView getUpdatePatient(@PathVariable(name = "id") long id, Model model) {
		model.addAttribute("info", patientInfoService.getPatientInfoById(id));

		return new ModelAndView("patient/update");
	}

	@PostMapping("/patient/update/{id}")
	public ModelAndView putUpdatePatient(@PathVariable(name = "id") long id, PatientInfo info, Model model) {
		log.debug(info.toString());
		info.setId(id);
		patientInfoService.updatePatientInfo(info);

		return new ModelAndView("redirect:/patient/list");
	}
}
