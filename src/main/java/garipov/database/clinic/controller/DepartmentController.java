package garipov.database.clinic.controller;

import garipov.database.clinic.dto.request.DepartmentRequest;
import garipov.database.clinic.dto.response.DepartmentResponse;
import garipov.database.clinic.entity.Department;
import garipov.database.clinic.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping("/{id}")
	public DepartmentResponse getById(@PathVariable Long id) {
		return toResponse(departmentService.getById(id));
	}

	@GetMapping
	public List<DepartmentResponse> getAll() {
		return departmentService.getAll()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DepartmentResponse create(@Valid @RequestBody DepartmentRequest request) {
		Department department = new Department();
		department.setName(request.getName());
		department.setFloor(request.getFloor());
		department.setDescription(request.getDescription());

		return toResponse(departmentService.create(department));
	}

	@PutMapping("/{id}")
	public DepartmentResponse update(
			@PathVariable Long id,
			@Valid @RequestBody DepartmentRequest request
	) {
		Department department = new Department();
		department.setName(request.getName());
		department.setFloor(request.getFloor());
		department.setDescription(request.getDescription());

		return toResponse(departmentService.update(id, department));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		departmentService.delete(id);
	}

	private DepartmentResponse toResponse(Department department) {
		return new DepartmentResponse(
				department.getId(),
				department.getName(),
				department.getFloor(),
				department.getDescription()
		);
	}
}