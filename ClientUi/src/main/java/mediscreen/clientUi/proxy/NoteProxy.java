package mediscreen.clientUi.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mediscreen.clientUi.bean.Note;

/*
 * Proxy for the Note microservice
 */
@FeignClient(name = "note", url = "${url.note}")
public interface NoteProxy {

	/**
	 * Creates a note.
	 *
	 * @param note the note to Create
	 * @return the note that was created
	 */
	@PostMapping(value = "/patHistory/add", consumes = "application/x-www-form-urlencoded")
	public Note createNote(Note note);

	/**
	 * Update a note.
	 *
	 * @param note the note to Update
	 * @return the note that was updated
	 */
	@PutMapping(value = "/patHistory/update", consumes = "application/x-www-form-urlencoded")
	public Note updateNote(Note note);

	/**
	 * Gets a precise note.
	 *
	 * @param id the id of the note to get
	 * @return the note associated with that id
	 */
	@GetMapping("/patHistory/search/{id}")
	public Note getNote(@PathVariable String id);

	/**
	 * Gets the note history of a patient.
	 *
	 * @param patId the id of the patient
	 * @return a List of Note associated with that patient
	 */
	@GetMapping("/patHistory")
	public List<Note> getPatientHistory(@RequestParam Long patId);
}
