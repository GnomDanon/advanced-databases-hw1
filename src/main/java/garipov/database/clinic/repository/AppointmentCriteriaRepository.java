package garipov.database.clinic.repository;

import garipov.database.clinic.dto.request.AppointmentFilter;
import garipov.database.clinic.entity.Appointment;

import java.util.List;

public interface AppointmentCriteriaRepository {

	List<Appointment> findByFilter(AppointmentFilter filter);
}