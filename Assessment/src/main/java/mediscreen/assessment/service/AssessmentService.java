package mediscreen.assessment.service;

import java.util.List;

import mediscreen.assessment.beans.Note;
import mediscreen.assessment.beans.PatientInfo;

public interface AssessmentService {

	/**
	 * Assess the probability of a patient of developping diabetes.
	 *
	 * @param notes the notes of the patient to evaluate
	 * @param info  the patientinfo of the patient to evaluate
	 * @return the result
	 */
	public String assess(List<Note> notes, PatientInfo info);
}
