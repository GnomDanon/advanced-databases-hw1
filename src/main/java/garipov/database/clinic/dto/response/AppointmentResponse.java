package garipov.database.clinic.dto.response;

import garipov.database.clinic.entity.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponse(
		Long id,
		LocalDateTime appointmentDate,
		String diagnosis,
		String notes,
		AppointmentStatus status,
		Long doctorId,
		Long patientId
) {
}