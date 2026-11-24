package garipov.database.clinic.controller;

import garipov.database.clinic.dto.request.AppointmentFilter;
import garipov.database.clinic.dto.request.AppointmentRequest;
import garipov.database.clinic.dto.response.AppointmentResponse;
import garipov.database.clinic.entity.Appointment;
import garipov.database.clinic.entity.enums.AppointmentStatus;
import garipov.database.clinic.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

	private final AppointmentService appointmentService;

	@GetMapping("/{id}")
	public AppointmentResponse getById(@PathVariable Long id) {
		return toResponse(appointmentService.getById(id));
	}

	@GetMapping
	public List<AppointmentResponse> getAll() {
		return appointmentService.getAll()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@GetMapping("/search")
	public List<AppointmentResponse> search(
			@ModelAttribute AppointmentFilter filter
	) {
		return appointmentService.search(filter)
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AppointmentResponse create(
			@Valid @RequestBody AppointmentRequest request
	) {
		Appointment appointment = new Appointment();
		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setDiagnosis(request.getDiagnosis());
		appointment.setNotes(request.getNotes());
		appointment.setStatus(request.getStatus());

		return toResponse(
				appointmentService.create(
						appointment,
						request.getDoctorId(),
						request.getPatientId()
				)
		);
	}

	@PatchMapping("/{id}/status")
	public AppointmentResponse updateStatus(
			@PathVariable Long id,
			@RequestParam AppointmentStatus status
	) {
		return toResponse(appointmentService.updateStatus(id, status));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		appointmentService.delete(id);
	}

	private AppointmentResponse toResponse(Appointment appointment) {
		return new AppointmentResponse(
				appointment.getId(),
				appointment.getAppointmentDate(),
				appointment.getDiagnosis(),
				appointment.getNotes(),
				appointment.getStatus(),
				appointment.getDoctor().getId(),
				appointment.getPatient().getId()
		);
	}
}