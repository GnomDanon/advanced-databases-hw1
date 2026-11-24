package garipov.database.clinic.dto.response;

import java.time.LocalDate;

public record PatientResponse(
		Long id,
		String firstName,
		String lastName,
		LocalDate birthDate,
		String phone
) {
}