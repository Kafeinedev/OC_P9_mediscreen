package mediscreen.patientInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

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

import mediscreen.patientInfo.exception.PatientInfoAlreadyExistException;
import mediscreen.patientInfo.model.PatientInfo;
import mediscreen.patientInfo.repository.PatientInfoRepository;
import mediscreen.patientInfo.service.impl.DefaultPatientInfoService;

@ExtendWith(MockitoExtension.class)
class DefaultPatientInfoServiceTest {

	@Mock
	private PatientInfoRepository mockRepository;

	@InjectMocks
	private DefaultPatientInfoService patientInfoService;

	private PatientInfo patientInfo;

	@BeforeEach
	void setUp() {
		patientInfo = new PatientInfo(1L, "test", "test", "2000-01-01", "apache helicopter", "a dress", "phon");
	}

	@Test
	void addPatientInfo_whenCalled_returnCreatedPatientInfo() throws PatientInfoAlreadyExistException {
		when(mockRepository.save(patientInfo)).thenReturn(patientInfo);

		PatientInfo test = patientInfoService.addPatientInfo(patientInfo);

		assertThat(test).isEqualTo(patientInfo);
	}

	@Test
	void addPatientInfo_whenPatientAlreadyExist_throw() {
		when(mockRepository.findById(any(Long.class))).thenReturn(Optional.of(patientInfo));

		assertThrows(PatientInfoAlreadyExistException.class, () -> patientInfoService.addPatientInfo(patientInfo));
	}

	@Test
	void addPatientInfo_whenCalled_useRepository() throws PatientInfoAlreadyExistException {
		patientInfoService.addPatientInfo(patientInfo);

		verify(mockRepository, times(1)).save(patientInfo);
		verify(mockRepository, times(1)).findById(any(Long.class));
	}

	@Test
	void getAllPatientInfo_whenNoPatientInfosAreFound_returnEmptyList() {
		when(mockRepository.findAll()).thenReturn(new ArrayList<PatientInfo>());

		List<PatientInfo> test = patientInfoService.getAllPatientInfo();

		assertThat(test).isEmpty();
	}

	@Test
	void getAllPatientInfo_whenPatientInfosAreFound_returnListOfPatientInfo() {
		when(mockRepository.findAll()).thenReturn(List.of(patientInfo));

		List<PatientInfo> test = patientInfoService.getAllPatientInfo();

		assertThat(test.size()).isEqualTo(1);
	}

	@Test
	void getAllPatientInfo_whenCalled_useRepository() {
		patientInfoService.getAllPatientInfo();

		verify(mockRepository, times(1)).findAll();
	}

	@Test
	void getPatientInfoByName_whenNoPatientInfosAreFound_throwNoSuchElementException() {
		when(mockRepository.findByFamily("test")).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> patientInfoService.getPatientInfoByName("test"));
	}

	@Test
	void getPatientInfoByName_whenPatientInfosAreFound_returnProperPatient() {
		when(mockRepository.findByFamily("test")).thenReturn(Optional.of(patientInfo));

		PatientInfo test = patientInfoService.getPatientInfoByName("test");

		assertThat(test).isEqualTo(patientInfo);
	}

	@Test
	void getPatientInfoByName_whenCalled_useRepository() {
		when(mockRepository.findByFamily("test")).thenReturn(Optional.of(patientInfo));
		patientInfoService.getPatientInfoByName("test");

		verify(mockRepository, times(1)).findByFamily("test");
	}

	@Test
	void getById_whenPatientInfoIsNotFound_throw() {
		when(mockRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> patientInfoService.getPatientInfoById(1));
	}

	@Test
	void geyById_whenPatientInfoIsFound_returnPatientInfo() {
		when(mockRepository.findById(1L)).thenReturn(Optional.of(patientInfo));

		PatientInfo test = patientInfoService.getPatientInfoById(1);

		assertThat(test).isEqualTo(patientInfo);
	}

	@Test
	void getById_whenCalled_useRepository() {
		when(mockRepository.findById(1L)).thenReturn(Optional.of(patientInfo));

		patientInfoService.getPatientInfoById(1);

		verify(mockRepository, times(1)).findById(1L);
	}

	@Test
	void updatePatientInfo_whenCalled_returnUpdatedPatientInfo() {
		when(mockRepository.findById(1L)).thenReturn(Optional.of(patientInfo));
		when(mockRepository.save(patientInfo)).thenReturn(patientInfo);

		PatientInfo test = patientInfoService.updatePatientInfo(patientInfo);

		assertThat(test).isEqualTo(patientInfo);
	}

	@Test
	void updatePatientInfo_whenPatientInfoDoesntExist_throw() {
		when(mockRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> patientInfoService.updatePatientInfo(patientInfo));
	}

	@Test
	void updatePatientInfo_whenCalled_useRepository() {
		when(mockRepository.findById(1L)).thenReturn(Optional.of(patientInfo));

		patientInfoService.updatePatientInfo(patientInfo);

		verify(mockRepository, times(1)).findById(1L);
		verify(mockRepository, times(1)).save(patientInfo);
	}

}
