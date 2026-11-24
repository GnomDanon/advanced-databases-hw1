package garipov.database.clinic.dto.request;

import garipov.database.clinic.entity.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentFilter {

	private Long patientId;
	private Long doctorId;
	private AppointmentStatus status;
}