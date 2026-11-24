package garipov.database.clinic.controller;

import garipov.database.clinic.dto.request.MedicalRecordRequest;
import garipov.database.clinic.dto.response.MedicalRecordResponse;
import garipov.database.clinic.entity.MedicalRecord;
import garipov.database.clinic.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

	private final MedicalRecordService medicalRecordService;

	@GetMapping("/{id}")
	public MedicalRecordResponse getById(@PathVariable Long id) {
		return toResponse(medicalRecordService.getById(id));
	}

	@GetMapping("/number/{number}")
	public MedicalRecordResponse getByNumber(@PathVariable String number) {
		return toResponse(medicalRecordService.getByNumber(number));
	}

	@GetMapping("/patient/{patientId}")
	public MedicalRecordResponse getByPatientId(@PathVariable Long patientId) {
		return toResponse(medicalRecordService.getByPatientId(patientId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MedicalRecordResponse create(
			@Valid @RequestBody MedicalRecordRequest request
	) {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setNumber(request.getNumber());
		medicalRecord.setMedicalHistory(request.getMedicalHistory());
		medicalRecord.setCreatedAt(LocalDate.now());

		return toResponse(
				medicalRecordService.create(medicalRecord, request.getPatientId())
		);
	}

	@PutMapping("/{id}")
	public MedicalRecordResponse update(
			@PathVariable Long id,
			@Valid @RequestBody MedicalRecordRequest request
	) {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setNumber(request.getNumber());
		medicalRecord.setMedicalHistory(request.getMedicalHistory());

		return toResponse(medicalRecordService.update(id, medicalRecord));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		medicalRecordService.delete(id);
	}

	private MedicalRecordResponse toResponse(MedicalRecord medicalRecord) {
		return new MedicalRecordResponse(
				medicalRecord.getId(),
				medicalRecord.getNumber(),
				medicalRecord.getCreatedAt(),
				medicalRecord.getMedicalHistory(),
				medicalRecord.getPatient().getId()
		);
	}
}