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

import mediscreen.clientUi.bean.PatientInfo;
import mediscreen.clientUi.controller.PatientInfoController;
import mediscreen.clientUi.proxy.PatientInfoProxy;

@WebMvcTest(PatientInfoController.class)
@ExtendWith(MockitoExtension.class)
class PatientInfoControllerTest {

	@MockBean
	private PatientInfoProxy mockService;

	@Autowired
	private MockMvc mockMvc;

	private PatientInfo test;

	@BeforeEach
	void setUp() {
		test = new PatientInfo(1L, "family", "given", "2000-01-01", "F", "a dress", "phon");
	}

	@Test
	void getAllPatients_whenCalled_return200() throws Exception {
		when(mockService.getAllPatientInfo()).thenReturn(List.of(test));

		mockMvc.perform(get("/patient/list")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void getSinglePatient_whenCalled_return200() throws Exception {
		when(mockService.getPatientInfoById(1)).thenReturn(test);

		mockMvc.perform(get("/patient/search/1")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void getPatientInfo_whenCalled_return200() throws Exception {
		when(mockService.getPatientInfoByName("thisis")).thenReturn(test);

		mockMvc.perform(get("/patient/search?family=thisis")).andExpect(status().is2xxSuccessful());

	}

	@Test
	void getAddPatient_whenCalled_return200() throws Exception {
		mockMvc.perform(get("/patient/add")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void postAddPatient_whenCalled_return302() throws Exception {
		mockMvc.perform(post("/patient/add").param("family", "family").param("given", "given")
				.param("dob", "2000-01-01").param("sex", "Mdrg").param("address", "a dress").param("phone", "phon"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	void getUpdatePatient_whenCalled_return200() throws Exception {
		when(mockService.getPatientInfoById(1L)).thenReturn(test);

		mockMvc.perform(get("/patient/update/1")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void putUpdatePatient_whenCalled_return302() throws Exception {
		mockMvc.perform(post("/patient/update/1").param("family", "family").param("given", "given")
				.param("dob", "2000-01-01").param("sex", "Mdrg").param("address", "a dress").param("phone", "phon"))
				.andExpect(status().is3xxRedirection());
	}
}
