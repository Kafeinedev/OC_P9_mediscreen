package mediscreen.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mediscreen.assessment.beans.Note;
import mediscreen.assessment.beans.PatientInfo;
import mediscreen.assessment.proxy.NoteProxy;
import mediscreen.assessment.proxy.PatientInfoProxy;
import mediscreen.assessment.service.AssessmentService;
import mediscreen.assessment.util.AgeCalculator;

/*
 * Controller to return the result of an assessment request by id or family name
 */
@Slf4j
@RestController
public class AssessmentController {

	@Autowired
	private NoteProxy noteService;

	@Autowired
	private PatientInfoProxy patientInfoService;

	@Autowired
	private AssessmentService assessmentService;

	/**
	 * Assess by id.
	 *
	 * @param id the id of the patient to assess
	 * @return the result in a form of a string
	 */
	@PostMapping("/assess/id")
	public String assessById(Long id) {
		log.info("post @ /assess/id id = " + id);
		PatientInfo info = patientInfoService.getPatientInfoById(id);
		List<Note> notes = noteService.getPatientHistory(id);

		return "Patient " + info.getGiven() + ' ' + info.getFamily() + " (age " + AgeCalculator.calculateAge(info)
				+ ") diabetes assessment is : " + assessmentService.assess(notes, info);
	}

	/**
	 * Assess by name.
	 *
	 * @param familyName the family name of the patient to assess
	 * @return the result in a form of a string
	 */
	@PostMapping("/assess/familyName")
	public String assessByName(String familyName) {
		log.info("post @ /assess/familyName familyName = " + familyName);
		PatientInfo info = patientInfoService.getPatientInfoByName(familyName);
		List<Note> notes = noteService.getPatientHistory(info.getId());

		return "Patient " + info.getGiven() + ' ' + info.getFamily() + " (age " + AgeCalculator.calculateAge(info)
				+ ") diabetes assessment is : " + assessmentService.assess(notes, info);
	}
}
