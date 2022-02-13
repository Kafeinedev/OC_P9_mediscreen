package mediscreen.clientUi;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import mediscreen.clientUi.bean.Note;
import mediscreen.clientUi.bean.PatientInfo;
import mediscreen.clientUi.controller.NoteController;
import mediscreen.clientUi.proxy.AssessmentProxy;
import mediscreen.clientUi.proxy.NoteProxy;
import mediscreen.clientUi.proxy.PatientInfoProxy;

@WebMvcTest(NoteController.class)
@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

	@MockBean
	private NoteProxy mockNoteService;

	@MockBean
	private PatientInfoProxy mockPatientService;

	@MockBean
	private AssessmentProxy mockAssessmentService;

	@Autowired
	private MockMvc mockMvc;

	private Note test;
	private PatientInfo info;

	@BeforeEach
	void setUp() {
		test = new Note("abé", 1L, "b", "v");
		info = new PatientInfo(1L, "family", "given", "2000-01-01", "F", "a dress", "phon");
	}

	@Test
	void getPatientHistory_whenCalled_return200() throws Exception {
		when(mockNoteService.getPatientHistory(1L)).thenReturn(List.of(test));
		when(mockPatientService.getPatientInfoById(1L)).thenReturn(info);

		mockMvc.perform(get("/patHistory/patient?patId=1")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void getNoteAdd_whenCalled_return200() throws Exception {
		when(mockPatientService.getPatientInfoById(1L)).thenReturn(info);

		mockMvc.perform(get("/patHistory/add/1")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void postNoteAdd_whenCalled_return302() throws Exception {
		when(mockNoteService.createNote(test)).thenReturn(test);

		mockMvc.perform(post("/patHistory/add/1").param("id", "id").param("patId", "1").param("patient", "patient")
				.param("note", "note")).andExpect(status().is3xxRedirection());

	}

	@Test
	void getNoteUpdate_whenCalled_return200() throws Exception {
		when(mockNoteService.getNote("id")).thenReturn(test);

		mockMvc.perform(get("/patHistory/update/id")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void postNoteUpdate_whenCalled_return200() throws Exception {
		when(mockNoteService.updateNote(test)).thenReturn(test);

		mockMvc.perform(post("/patHistory/update/id").param("id", "id").param("patId", "1").param("patient", "patient")
				.param("note", "note")).andExpect(status().is3xxRedirection());
	}
}
