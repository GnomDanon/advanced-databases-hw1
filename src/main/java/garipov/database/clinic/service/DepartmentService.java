package garipov.database.clinic.service;

import garipov.database.clinic.entity.Department;
import garipov.database.clinic.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

	private final DepartmentRepository departmentRepository;

	public Department getById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Department not found: " + id
				));
	}

	public List<Department> getAll() {
		return departmentRepository.findAll();
	}

	@Transactional
	public Department create(Department department) {
		return departmentRepository.save(department);
	}

	@Transactional
	public Department update(Long id, Department updatedDepartment) {
		Department department = getById(id);

		department.setName(updatedDepartment.getName());
		department.setFloor(updatedDepartment.getFloor());
		department.setDescription(updatedDepartment.getDescription());

		return department;
	}

	@Transactional
	public void delete(Long id) {
		Department department = getById(id);

		if (departmentRepository.existsByIdAndDoctorsIsNotEmpty(id)) {
			throw new IllegalStateException(
					"Cannot delete department with assigned doctors: " + id
			);
		}

		departmentRepository.delete(department);
	}
}
