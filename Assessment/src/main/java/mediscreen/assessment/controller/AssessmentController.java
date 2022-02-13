package mediscreen.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.assessment.proxy.NoteProxy;
import mediscreen.assessment.proxy.PatientInfoProxy;
import mediscreen.assessment.service.AssessmentService;

@RestController
public class AssessmentController {

	@Autowired
	private NoteProxy noteService;

	@Autowired
	private PatientInfoProxy patientInfoProxy;

	@Autowired
	private AssessmentService assessmentService;

	@PostMapping("/assess/id")
	public String assessById(long id) {
		return null;
	}

	@PostMapping("/assess/id")
	public String assessByName(String name) {
		return null;
	}
}
