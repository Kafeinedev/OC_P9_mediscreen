package mediscreen.notes.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mediscreen.notes.exception.NoteAlreadyExistException;
import mediscreen.notes.model.Note;
import mediscreen.notes.repository.NoteRepository;
import mediscreen.notes.service.NoteService;

@Slf4j
@Service
public class DefaultNoteService implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public Note addNote(Note toAdd) throws NoteAlreadyExistException {
		log.trace("Adding new note");
		if (toAdd.getId() != null && noteRepository.findById(toAdd.getId()).isPresent()) {
			log.error("Trying to add already existing patientInfo id : " + toAdd.getId());
			throw new NoteAlreadyExistException();
		}
		return noteRepository.save(toAdd);
	}

	@Override
	public Note updateNote(Note toUpdate) {
		log.trace("Updating note id : " + toUpdate.getId());
		noteRepository.findById(toUpdate.getId()).orElseThrow(() -> {
			log.error("Could not find the note with id : " + toUpdate.getId());
			return new NoSuchElementException();
		});

		return noteRepository.save(toUpdate);
	}

	@Override
	public Note getById(String id) {
		log.trace("Getting note via id : " + id);
		return noteRepository.findById(id).orElseThrow(() -> {
			log.error("Could not find the note with id : " + id);
			return new NoSuchElementException();
		});
	}

	@Override
	public List<Note> getAllPatientNote(Long patId) {
		log.trace("Getting all notes of a patient");
		return noteRepository.findAllByPatId(patId);
	}

}
