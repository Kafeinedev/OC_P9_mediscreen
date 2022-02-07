package mediscreen.notes.service;

import java.util.List;

import mediscreen.notes.exception.NoteAlreadyExistException;
import mediscreen.notes.model.Note;

public interface NoteService {

	public Note addNote(Note toAdd) throws NoteAlreadyExistException;

	public Note updateNote(Note toUpdate);

	public Note getById(String id);

	public List<Note> getAllPatientNote(Long patId);
}
