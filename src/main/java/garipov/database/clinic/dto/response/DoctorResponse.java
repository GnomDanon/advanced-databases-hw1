package garipov.database.clinic.dto.response;

public record DoctorResponse(
		Long id,
		String firstName,
		String lastName,
		String specialization,
		Integer experienceYears,
		Long departmentId
) {
}