package mediscreen.assessment.service;

import java.util.List;

import mediscreen.assessment.beans.Note;

public interface AssessmentService {

	public String assess(List<Note> notes);
}