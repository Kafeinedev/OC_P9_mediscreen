package mediscreen.patientInfo;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import mediscreen.patientInfo.controller.PatientInfoController;
import mediscreen.patientInfo.model.PatientInfo;
import mediscreen.patientInfo.service.PatientInfoService;

@WebMvcTest(PatientInfoController.class)
@ExtendWith(MockitoExtension.class)
class PatientInfoControllerTest {

	@MockBean
	private PatientInfoService mockService;

	@Autowired
	private MockMvc mockMvc;

	private PatientInfo test;

	@BeforeEach
	void setUp() {
		test = new PatientInfo(1L, "family", "given", "2000-01-01", "F", "a dress", "phon");
	}

	@Test
	void addPatientInfo_whenCalledWithValidPatientInfo_return201AndCreatedRessource() throws Exception {
		when(mockService.addPatientInfo(any(PatientInfo.class))).thenReturn(test);
		test.getAddress();
		mockMvc.perform(post("/patient/add").param("id", "1").param("family", "family").param("given", "given")
				.param("dob", "2000-01-01").param("sex", "F").param("address", "a dress").param("phone", "phon"))
				.andExpect(status().isCreated())
				.andExpect(content().string("{\"id\":1,\"family\":\"family\",\"given\":\"given\",\"dob\""
						+ ":\"2000-01-01\",\"sex\":\"F\",\"address\":\"a dress\",\"phone\":\"phon\"}"));
	}

	@Test
	void updatePatientInfo_whenCalledWithValidPatientInfo_return200AndUpdatedRessource() throws Exception {
		when(mockService.updatePatientInfo(any(PatientInfo.class))).thenReturn(test);

	}

	@Test
	void getPatientInfoById_whenCalledWithExistingId_return200AssociatedRessources() throws Exception {
		when(mockService.getPatientInfoById(1L)).thenReturn(test);

		mockMvc.perform(get("/patient/1")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("{\"id\":1,\"family\":\"family\",\"given\":\"given\",\"dob\""
						+ ":\"2000-01-01\",\"sex\":\"F\",\"address\":\"a dress\",\"phone\":\"phon\"}"));
	}

	@Test
	void getPatientInfoByName_whenCalled_return200AssociatedRessources() throws Exception {
		when(mockService.getPatientInfoByName("test", "test")).thenReturn(List.of(test));

		mockMvc.perform(get("/patient/search?family=test&given=test")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("[{\"id\":1,\"family\":\"family\",\"given\":\"given\",\"dob\""
						+ ":\"2000-01-01\",\"sex\":\"F\",\"address\":\"a dress\",\"phone\":\"phon\"}]"));
	}

	@Test
	void getAllPatientInfo_whenCalled_return200AssociatedRessources() throws Exception {
		when(mockService.getAllPatientInfo()).thenReturn(List.of(test));

		mockMvc.perform(get("/patient/list")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("[{\"id\":1,\"family\":\"family\",\"given\":\"given\",\"dob\""
						+ ":\"2000-01-01\",\"sex\":\"F\",\"address\":\"a dress\",\"phone\":\"phon\"}]"));
	}

}
