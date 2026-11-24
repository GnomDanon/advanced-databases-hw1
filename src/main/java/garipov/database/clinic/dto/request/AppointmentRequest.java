package garipov.database.clinic.dto.request;

import garipov.database.clinic.entity.enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequest {

	@NotNull
	private LocalDateTime appointmentDate;

	private String diagnosis;

	private String notes;

	@NotNull
	private AppointmentStatus status;

	@NotNull
	private Long doctorId;

	@NotNull
	private Long patientId;
}