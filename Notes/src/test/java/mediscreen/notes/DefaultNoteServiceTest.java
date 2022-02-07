package mediscreen.notes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mediscreen.notes.exception.NoteAlreadyExistException;
import mediscreen.notes.model.Note;
import mediscreen.notes.repository.NoteRepository;
import mediscreen.notes.service.impl.DefaultNoteService;

@ExtendWith(MockitoExtension.class)
class DefaultNoteServiceTest {

	@Mock
	private NoteRepository mockRepository;

	@InjectMocks
	private DefaultNoteService noteService;

	private Note note;

	@BeforeEach
	void setUp() {
		note = new Note("id", 1L, "patient", "nooooooooooooooote");
	}

	@Test
	void addNote_whenCalled_returnCreatedNote() throws NoteAlreadyExistException {
		when(mockRepository.save(note)).thenReturn(note);

		Note test = noteService.addNote(note);

		assertThat(test).isEqualTo(note);
	}

	@Test
	void addNote_whenNoteAlreadyExist_throw() {
		when(mockRepository.findById(any(String.class))).thenReturn(Optional.of(note));

		assertThrows(NoteAlreadyExistException.class, () -> noteService.addNote(note));
	}

	@Test
	void addNote_whenCalled_useRepository() throws NoteAlreadyExistException {
		noteService.addNote(note);

		verify(mockRepository, times(1)).save(note);
		verify(mockRepository, times(1)).findById(any(String.class));
	}

	@Test
	void getAllPatientNote_whenNoNotesAreFound_returnEmptyList() {
		when(mockRepository.findAllByPatId(any(Long.class))).thenReturn(new ArrayList<Note>());

		List<Note> test = noteService.getAllPatientNote(1L);

		assertThat(test).isEmpty();
	}

	@Test
	void getAllPatientNote_whenNotesAreFound_returnListOfNote() {
		when(mockRepository.findAllByPatId(any(Long.class))).thenReturn(List.of(note));

		List<Note> test = noteService.getAllPatientNote(1L);

		assertThat(test.size()).isEqualTo(1);
	}

	@Test
	void getAllNote_whenCalled_useRepository() {
		noteService.getAllPatientNote(1L);

		verify(mockRepository, times(1)).findAllByPatId(1L);
	}

	@Test
	void getById_whenNoteIsNotFound_throw() {
		when(mockRepository.findById(any(String.class))).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> noteService.getById("hello"));
	}

	@Test
	void geyById_whenNoteIsFound_returnNote() {
		when(mockRepository.findById(any(String.class))).thenReturn(Optional.of(note));

		Note test = noteService.getById("test");

		assertThat(test).isEqualTo(note);
	}

	@Test
	void getById_whenCalled_useRepository() {
		when(mockRepository.findById("hallo")).thenReturn(Optional.of(note));

		noteService.getById("hallo");

		verify(mockRepository, times(1)).findById("hallo");
	}

	@Test
	void updateNote_whenCalled_returnUpdatedNote() {
		when(mockRepository.findById("id")).thenReturn(Optional.of(note));
		when(mockRepository.save(note)).thenReturn(note);

		Note test = noteService.updateNote(note);

		assertThat(test).isEqualTo(note);
	}

	@Test
	void updateNote_whenNoteDoesntExist_throw() {
		when(mockRepository.findById("id")).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> noteService.updateNote(note));
	}

	@Test
	void updateNote_whenCalled_useRepository() {
		when(mockRepository.findById("id")).thenReturn(Optional.of(note));

		noteService.updateNote(note);

		verify(mockRepository, times(1)).findById("id");
		verify(mockRepository, times(1)).save(note);
	}

}
