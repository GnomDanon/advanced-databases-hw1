package garipov.database.clinic.service;

import garipov.database.clinic.entity.Patient;
import garipov.database.clinic.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientService {

	private final PatientRepository patientRepository;

	public Patient getById(Long id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Patient not found: " + id
				));
	}

	public List<Patient> getAll() {
		return patientRepository.findAll();
	}

	public List<Patient> searchByLastName(String lastName) {
		return patientRepository.findByLastNameContainingIgnoreCase(lastName);
	}

	public Patient getByPhone(String phone) {
		return patientRepository.findByPhoneNative(phone)
				.orElseThrow(() -> new EntityNotFoundException(
						"Patient not found: " + phone
				));
	}

	@Transactional
	public Patient create(Patient patient) {
		return patientRepository.save(patient);
	}

	@Transactional
	public void delete(Long id) {
		Patient patient = getById(id);
		patientRepository.delete(patient);
	}
}