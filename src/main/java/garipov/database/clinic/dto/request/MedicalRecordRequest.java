package garipov.database.clinic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordRequest {

	@NotBlank
	private String number;

	@NotBlank
	private String medicalHistory;

	@NotNull
	private Long patientId;
}