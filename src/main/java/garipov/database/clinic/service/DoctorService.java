package garipov.database.clinic.service;

import garipov.database.clinic.entity.Department;
import garipov.database.clinic.entity.Doctor;
import garipov.database.clinic.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorService {

	private final DoctorRepository doctorRepository;

	private final DepartmentService departmentService;

	public Doctor getById(Long id) {
		return doctorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Doctor not found: " + id
				));
	}

	public List<Doctor> getAll() {
		return doctorRepository.findAll();
	}

	public List<Doctor> getByDepartmentId(Long departmentId) {
		return doctorRepository.findByDepartment_Id(departmentId);
	}

	@Transactional
	public Doctor create(Doctor doctor, Long departmentId) {
		Department department = departmentService.getById(departmentId);
		department.addDoctor(doctor);
		return doctorRepository.save(doctor);
	}

	@Transactional
	public Doctor transferToDepartment(Long doctorId, Long departmentId) {
		Doctor doctor = getById(doctorId);
		Department department = departmentService.getById(departmentId);
		department.addDoctor(doctor);
		return doctor;
	}

	@Transactional
	public void delete(Long id) {
		Doctor doctor = getById(id);

		if (doctorRepository.existsByIdAndAppointmentsIsNotEmpty(id)) {
			throw new IllegalStateException(
					"Cannot delete doctor with assigned appointments: " + id
			);
		}

		doctorRepository.delete(doctor);
	}
}
