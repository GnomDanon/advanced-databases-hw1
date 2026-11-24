package garipov.database.clinic.service;

import garipov.database.clinic.dto.request.AppointmentFilter;
import garipov.database.clinic.entity.Appointment;
import garipov.database.clinic.entity.enums.AppointmentStatus;
import garipov.database.clinic.entity.Doctor;
import garipov.database.clinic.entity.Patient;
import garipov.database.clinic.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final DoctorService doctorService;
	private final PatientService patientService;

	public Appointment getById(Long id) {
		return appointmentRepository.findByIdWithDetails(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Appointment not found: " + id
				));
	}

	public List<Appointment> getAll() {
		return appointmentRepository.findAll();
	}

	public List<Appointment> getByPatientId(Long patientId) {
		return appointmentRepository.findByPatient_Id(patientId);
	}

	public List<Appointment> getByDoctorId(Long doctorId) {
		return appointmentRepository.findByDoctor_Id(doctorId);
	}

	public List<Appointment> search(AppointmentFilter filter) {
		return appointmentRepository.findByFilter(filter);
	}

	@Transactional
	public Appointment create(
			Appointment appointment,
			Long doctorId,
			Long patientId
	) {
		Doctor doctor = doctorService.getById(doctorId);
		Patient patient = patientService.getById(patientId);

		doctor.addAppointment(appointment);
		patient.addAppointment(appointment);

		return appointmentRepository.save(appointment);
	}

	@Transactional
	public Appointment updateStatus(Long id, AppointmentStatus status) {
		Appointment appointment = getById(id);
		appointment.setStatus(status);
		return appointment;
	}

	@Transactional
	public void delete(Long id) {
		Appointment appointment = getById(id);
		appointmentRepository.delete(appointment);
	}
}