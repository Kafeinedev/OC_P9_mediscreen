package mediscreen.assessment;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import mediscreen.assessment.beans.Note;
import mediscreen.assessment.beans.PatientInfo;
import mediscreen.assessment.controller.AssessmentController;
import mediscreen.assessment.proxy.NoteProxy;
import mediscreen.assessment.proxy.PatientInfoProxy;
import mediscreen.assessment.service.AssessmentService;

@WebMvcTest(AssessmentController.class)
@ExtendWith(MockitoExtension.class)
class AssessmentControllerTest {

	@MockBean
	private NoteProxy mockNoteService;

	@MockBean
	private PatientInfoProxy mockPatientService;

	@MockBean
	private AssessmentService mockAssessmentService;

	@Autowired
	private MockMvc mockMvc;

	private List<Note> notes = new ArrayList<Note>();
	private PatientInfo info = new PatientInfo(1L, "a", "b", "2000-01-01", "d", null, null);

	@Test
	void assessById_whenCalled_return200andpropercontent() throws Exception {
		when(mockNoteService.getPatientHistory(1L)).thenReturn(notes);
		when(mockPatientService.getPatientInfoById(1L)).thenReturn(info);
		when(mockAssessmentService.assess(notes, info)).thenReturn("yup");
		mockMvc.perform(post("/assess/id").param("id", "1")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("Patient b a (age 22) diabetes assessment is : yup"));
	}

	@Test
	void assessByName_whenCalled_return200andpropercontent() throws Exception {
		when(mockNoteService.getPatientHistory(1L)).thenReturn(notes);
		when(mockPatientService.getPatientInfoByName("name")).thenReturn(info);
		when(mockAssessmentService.assess(notes, info)).thenReturn("yup");
		mockMvc.perform(post("/assess/familyName").param("familyName", "name")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("Patient b a (age 22) diabetes assessment is : yup"));
	}
}
