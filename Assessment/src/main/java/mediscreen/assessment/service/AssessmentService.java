package mediscreen.assessment.service;

import java.util.List;

import mediscreen.assessment.beans.Note;
import mediscreen.assessment.beans.PatientInfo;

public interface AssessmentService {

	public String assess(List<Note> notes, PatientInfo info);
}
