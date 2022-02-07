package mediscreen.notes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import mediscreen.notes.controller.NoteController;
import mediscreen.notes.exception.NoteAlreadyExistException;
import mediscreen.notes.model.Note;
import mediscreen.notes.service.NoteService;

@WebMvcTest(NoteController.class)
@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

	@MockBean
	private NoteService mockService;

	@Autowired
	private MockMvc mockMvc;

	private Note test;

	@BeforeEach
	void setUp() {
		test = new Note("id", 1L, "patient", "note");
	}

	@Test
	void addNote_whenCalledWithValidNote_return201AndCreatedRessource() throws Exception {
		when(mockService.addNote(any(Note.class))).thenReturn(test);

		mockMvc.perform(post("/patHistory/add").param("patId", "1").param("patient", "patient").param("note",
				"noooooooooooooooote")).andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":\"id\",\"patId\":1,\"patient\":\"patient\",\"note\":\"note\"}"));
	}

	@Test
	void addNote_whenCalledWithInvalidNote_return400() throws Exception {
		mockMvc.perform(
				post("/patHistory/add").param("patId", "1").param("patient", "").param("note", "noooooooooooooooote"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void addNote_whenCalledWithAlreadyExistingNoteId_return303() throws Exception {
		when(mockService.addNote(any(Note.class))).thenThrow(new NoteAlreadyExistException());

		mockMvc.perform(post("/patHistory/add").param("patId", "1").param("patient", "patient").param("note",
				"noooooooooooooooote")).andExpect(status().isSeeOther());
	}

	@Test
	void updateNote_whenCalledWithValidNote_return200AndUpdatedRessource() throws Exception {
		when(mockService.updateNote(any(Note.class))).thenReturn(test);

		mockMvc.perform(put("/patHistory/update").param("patId", "1").param("patient", "updated").param("note",
				"noooooooooooooooote")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("{\"id\":\"id\",\"patId\":1,\"patient\":\"patient\",\"note\":\"note\"}"));
	}

	@Test
	void updateNote_whenCalledWithInvalidNote_return400() throws Exception {
		mockMvc.perform(
				put("/patHistory/update").param("patId", "1").param("patient", "").param("note", "noooooooooooooooote"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void getNote_whenCalledWithExistingId_return200AssociatedRessources() throws Exception {
		when(mockService.getById("id")).thenReturn(test);

		mockMvc.perform(get("/patHistory/search/id")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("{\"id\":\"id\",\"patId\":1,\"patient\":\"patient\",\"note\":\"note\"}"));
	}

	@Test
	void getNote_whenCalledWithNonExistingId_return404() throws Exception {
		when(mockService.getById("id")).thenThrow(new NoSuchElementException());

		mockMvc.perform(get("/patHistory/search/id")).andExpect(status().is4xxClientError());
	}

	@Test
	void getAllPatientNote_whenCalled_return200AssociatedRessources() throws Exception {
		when(mockService.getAllPatientNote(1L)).thenReturn(List.of(test));

		mockMvc.perform(get("/patHistory?patId=1")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("[{\"id\":\"id\",\"patId\":1,\"patient\":\"patient\",\"note\":\"note\"}]"));
	}

}
