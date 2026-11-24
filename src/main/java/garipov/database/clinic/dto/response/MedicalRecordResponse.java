package garipov.database.clinic.dto.response;

import java.time.LocalDate;

public record MedicalRecordResponse(
		Long id,
		String number,
		LocalDate createdAt,
		String medicalHistory,
		Long patientId
) {
}