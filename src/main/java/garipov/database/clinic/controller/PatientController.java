package garipov.database.clinic.controller;

import garipov.database.clinic.dto.request.PatientRequest;
import garipov.database.clinic.dto.response.PatientResponse;
import garipov.database.clinic.entity.Patient;
import garipov.database.clinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;

	@GetMapping("/{id}")
	public PatientResponse getById(@PathVariable Long id) {
		return toResponse(patientService.getById(id));
	}

	@GetMapping
	public List<PatientResponse> getAll() {
		return patientService.getAll()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@GetMapping("/search")
	public List<PatientResponse> searchByLastName(
			@RequestParam String lastName
	) {
		return patientService.searchByLastName(lastName)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@GetMapping("/phone/{phone}")
	public PatientResponse getByPhone(@PathVariable String phone) {
		return toResponse(patientService.getByPhone(phone));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PatientResponse create(@Valid @RequestBody PatientRequest request) {
		Patient patient = new Patient();
		patient.setFirstName(request.getFirstName());
		patient.setLastName(request.getLastName());
		patient.setBirthDate(request.getBirthDate());
		patient.setPhone(request.getPhone());

		return toResponse(patientService.create(patient));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		patientService.delete(id);
	}

	private PatientResponse toResponse(Patient patient) {
		return new PatientResponse(
				patient.getId(),
				patient.getFirstName(),
				patient.getLastName(),
				patient.getBirthDate(),
				patient.getPhone()
		);
	}
}