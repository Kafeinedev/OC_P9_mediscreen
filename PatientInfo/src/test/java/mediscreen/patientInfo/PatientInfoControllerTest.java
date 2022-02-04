package mediscreen.patientInfo;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
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

import mediscreen.patientInfo.controller.PatientInfoController;
import mediscreen.patientInfo.exception.PatientInfoAlreadyExistException;
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
	void addPatientInfo_whenCalledWithInvalidPatientInfo_return400() throws Exception {
		mockMvc.perform(post("/patient/add").param("id", "1").param("family", "family").param("given", "given")
				.param("dob", "2000-01-01").param("sex", "apache").param("address", "a dress").param("phone", "phon"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void addPatientInfo_whenCalledWithAlreadyExistingPatientInfoId_return303() throws Exception {
		when(mockService.addPatientInfo(any(PatientInfo.class))).thenThrow(new PatientInfoAlreadyExistException());

		mockMvc.perform(post("/patient/add").param("id", "1").param("family", "family").param("given", "given")
				.param("dob", "2000-01-01").param("sex", "M").param("address", "a dress").param("phone", "phon"))
				.andExpect(status().isSeeOther());
	}

	@Test
	void updatePatientInfo_whenCalledWithValidPatientInfo_return200AndUpdatedRessource() throws Exception {
		when(mockService.updatePatientInfo(any(PatientInfo.class))).thenReturn(test);

		mockMvc.perform(put("/patient/update").param("id", "1").param("family", "family").param("given", "given")
				.param("dob", "2000-01-01").param("sex", "M").param("address", "a dress").param("phone", "phon"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void updatePatientInfo_whenCalledWithInvalidPatientInfo_return400() throws Exception {
		mockMvc.perform(put("/patient/update").param("id", "1").param("family", "family").param("given", "given")
				.param("dob", "2000-01-01").param("sex", "Mdrg").param("address", "a dress").param("phone", "phon"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void getPatientInfoById_whenCalledWithExistingId_return200AssociatedRessources() throws Exception {
		when(mockService.getPatientInfoById(1L)).thenReturn(test);

		mockMvc.perform(get("/patient/1")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("{\"id\":1,\"family\":\"family\",\"given\":\"given\",\"dob\""
						+ ":\"2000-01-01\",\"sex\":\"F\",\"address\":\"a dress\",\"phone\":\"phon\"}"));
	}

	@Test
	void getPatientInfoById_whenCalledWithNonExistingId_return404() throws Exception {
		when(mockService.getPatientInfoById(1L)).thenThrow(new NoSuchElementException());

		mockMvc.perform(get("/patient/1")).andExpect(status().is4xxClientError());
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
