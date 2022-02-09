package mediscreen.notes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mediscreen.notes.exception.NoteAlreadyExistException;
import mediscreen.notes.model.Note;
import mediscreen.notes.service.NoteService;

/*
 * CRUD controller for Note
 */
@Slf4j
@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	/**
	 * Creates the note.
	 *
	 * @param note the note to create
	 * @return the note created
	 * @throws NoteAlreadyExistException if the Note given posses an id that already
	 *                                   exist
	 */
	@PostMapping("/patHistory/add")
	@ResponseStatus(HttpStatus.CREATED)
	public Note createNote(@Valid Note note) throws NoteAlreadyExistException {
		log.info("Post @ /patHistory/add");
		return noteService.addNote(note);
	}

	/**
	 * Update note.
	 *
	 * @param note the note to update
	 * @return the updated note
	 */
	@PutMapping("/patHistory/update")
	public Note updateNote(@Valid Note note) {
		log.info("Put @ /patHistory/update");
		return noteService.updateNote(note);
	}

	/**
	 * Gets the note by id.
	 *
	 * @param id the id of the note to get
	 * @return the note associated with that id
	 */
	@GetMapping("/patHistory/search/{id}")
	public Note getNote(@PathVariable String id) {
		log.info("Get @ /patHistory/search" + id);
		return noteService.getById(id);
	}

	/**
	 * Gets the patient note history.
	 *
	 * @param patId the patient id
	 * @return a list of note associated with the patient
	 */
	@GetMapping("/patHistory")
	public List<Note> getPatientHistory(@RequestParam Long patId) {
		log.info("Get @ /patHistory/patient/" + patId);
		return noteService.getAllPatientNote(patId);
	}
}
