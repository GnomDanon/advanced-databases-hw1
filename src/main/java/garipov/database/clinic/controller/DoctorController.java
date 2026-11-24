package garipov.database.clinic.controller;

import garipov.database.clinic.dto.request.DoctorRequest;
import garipov.database.clinic.dto.response.DoctorResponse;
import garipov.database.clinic.entity.Doctor;
import garipov.database.clinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorService doctorService;

	@GetMapping("/{id}")
	public DoctorResponse getById(@PathVariable Long id) {
		return toResponse(doctorService.getById(id));
	}

	@GetMapping
	public List<DoctorResponse> getAll() {
		return doctorService.getAll()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@GetMapping("/department/{departmentId}")
	public List<DoctorResponse> getByDepartmentId(@PathVariable Long departmentId) {
		return doctorService.getByDepartmentId(departmentId)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DoctorResponse create(@Valid @RequestBody DoctorRequest request) {
		Doctor doctor = new Doctor();
		doctor.setFirstName(request.getFirstName());
		doctor.setLastName(request.getLastName());
		doctor.setSpecialization(request.getSpecialization());
		doctor.setExperienceYears(request.getExperienceYears());

		return toResponse(
				doctorService.create(doctor, request.getDepartmentId())
		);
	}

	@PutMapping("/{id}/department")
	public DoctorResponse transferToDepartment(
			@PathVariable Long id,
			@RequestParam Long departmentId
	) {
		return toResponse(
				doctorService.transferToDepartment(id, departmentId)
		);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		doctorService.delete(id);
	}

	private DoctorResponse toResponse(Doctor doctor) {
		return new DoctorResponse(
				doctor.getId(),
				doctor.getFirstName(),
				doctor.getLastName(),
				doctor.getSpecialization(),
				doctor.getExperienceYears(),
				doctor.getDepartment().getId()
		);
	}
}