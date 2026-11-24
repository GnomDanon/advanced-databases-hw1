package garipov.database.clinic.service;

import garipov.database.clinic.entity.MedicalRecord;
import garipov.database.clinic.entity.Patient;
import garipov.database.clinic.repository.MedicalRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalRecordService {

	private final MedicalRecordRepository medicalRecordRepository;

	private final PatientService patientService;

	public MedicalRecord getById(Long id) {
		return medicalRecordRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Medical record not found: " + id
				));
	}

	public MedicalRecord getByNumber(String number) {
		return medicalRecordRepository.findByNumber(number)
				.orElseThrow(() -> new EntityNotFoundException(
						"Medical record not found: " + number
				));
	}

	public MedicalRecord getByPatientId(Long patientId) {
		return medicalRecordRepository.findByPatient_Id(patientId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Medical record not found: " + patientId
				));
	}

	@Transactional
	public MedicalRecord create(MedicalRecord medicalRecord, Long patientId) {
		Patient patient = patientService.getById(patientId);

		if (patient.getMedicalRecord() != null) {
			throw new IllegalStateException(
					"Patient already has a medical record: " + patientId
			);
		}

		patient.setMedicalRecord(medicalRecord);
		return medicalRecord;
	}

	@Transactional
	public MedicalRecord update(Long id, MedicalRecord updatedMedicalRecord) {
		MedicalRecord medicalRecord = getById(id);

		medicalRecord.setNumber(updatedMedicalRecord.getNumber());
		medicalRecord.setMedicalHistory(updatedMedicalRecord.getMedicalHistory());

		return medicalRecord;
	}

	@Transactional
	public void delete(Long id) {
		MedicalRecord medicalRecord = getById(id);
		Patient patient = patientService.getById(
				medicalRecord.getPatient().getId()
		);

		patient.removeMedicalRecord();
	}
}
