package garipov.database.clinic.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequest {

	@NotBlank
	private String name;

	@NotNull
	@Min(1)
	private Integer floor;

	private String description;
}