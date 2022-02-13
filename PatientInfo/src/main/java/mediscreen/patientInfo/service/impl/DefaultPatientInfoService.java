package mediscreen.patientInfo.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mediscreen.patientInfo.exception.PatientInfoAlreadyExistException;
import mediscreen.patientInfo.model.PatientInfo;
import mediscreen.patientInfo.repository.PatientInfoRepository;
import mediscreen.patientInfo.service.PatientInfoService;

@Service
@Slf4j
@Transactional
public class DefaultPatientInfoService implements PatientInfoService {

	@Autowired
	private PatientInfoRepository patientInfoRepository;

	@Override
	public PatientInfo addPatientInfo(PatientInfo toCreate) throws PatientInfoAlreadyExistException {
		log.trace("Adding new patient info");
		if (toCreate.getId() != null && patientInfoRepository.findById(toCreate.getId()).isPresent()) {
			log.error("Trying to add already existing patientInfo id : " + toCreate.getId());
			throw new PatientInfoAlreadyExistException();
		}
		return patientInfoRepository.save(toCreate);
	}

	@Override
	public PatientInfo updatePatientInfo(PatientInfo toUpdate) {
		log.trace("Updating patient info id : " + toUpdate.getId());
		patientInfoRepository.findById(toUpdate.getId()).orElseThrow(() -> {
			log.error("Could not find the patientInfo with id : " + toUpdate.getId());
			return new NoSuchElementException();
		});

		return patientInfoRepository.save(toUpdate);
	}

	@Override
	public PatientInfo getPatientInfoById(long id) {
		log.trace("Getting patient info via id : " + id);
		return patientInfoRepository.findById(id).orElseThrow(() -> {
			log.error("Could not find the patientInfo with id : " + id);
			return new NoSuchElementException();
		});
	}

	@Override
	public PatientInfo getPatientInfoByName(String family) {
		log.trace("Getting patient info via name : " + family);
		return patientInfoRepository.findByFamily(family).orElseThrow(() -> {
			log.error("Could not find the patientInfo with family name : " + family);
			return new NoSuchElementException();
		});
	}

	@Override
	public List<PatientInfo> getAllPatientInfo() {
		log.trace("Getting all patient info");
		return patientInfoRepository.findAll();
	}
}
