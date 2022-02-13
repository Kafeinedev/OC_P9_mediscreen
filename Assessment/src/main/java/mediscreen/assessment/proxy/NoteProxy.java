package mediscreen.assessment.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mediscreen.assessment.beans.Note;

/*
 * Proxy for the Note microservice
 */
@FeignClient(name = "note", url = "${url.note}")
public interface NoteProxy {
	/**
	 * Gets the note history of a patient.
	 *
	 * @param patId the id of the patient
	 * @return a List of Note associated with that patient
	 */
	@GetMapping("/patHistory")
	public List<Note> getPatientHistory(@RequestParam Long patId);
}