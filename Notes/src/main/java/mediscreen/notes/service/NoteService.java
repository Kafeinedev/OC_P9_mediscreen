package mediscreen.notes.service;

import java.util.List;

import mediscreen.notes.exception.NoteAlreadyExistException;
import mediscreen.notes.model.Note;

/**
 * CRUD service for Note.
 */
public interface NoteService {

	/**
	 * Adds the note.
	 *
	 * @param toAdd the note to add
	 * @return the note that was created
	 * @throws NoteAlreadyExistException if the Note given posses an id that already
	 *                                   exist
	 */
	public Note addNote(Note toAdd) throws NoteAlreadyExistException;

	/**
	 * Update note.
	 *
	 * @param toUpdate the note to update
	 * @return the updated note
	 */
	public Note updateNote(Note toUpdate);

	/**
	 * Gets the note by id.
	 *
	 * @param id the id of the note to find
	 * @return the note associated with that id
	 */
	public Note getById(String id);

	/**
	 * Gets the all patient note.
	 *
	 * @param patId the id of the patient
	 * @return a list of all the note associated with that patient
	 */
	public List<Note> getAllPatientNote(Long patId);
}
