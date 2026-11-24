package garipov.database.clinic.dto.response;

public record DepartmentResponse(
		Long id,
		String name,
		Integer floor,
		String description
) {
}