package garipov.database.clinic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotNull
	@Past
	private LocalDate birthDate;

	@NotBlank
	@Pattern(regexp = "^\\+?[0-9]{10,15}$")
	private String phone;
}