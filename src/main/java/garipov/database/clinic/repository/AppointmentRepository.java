package garipov.database.clinic.repository;

import garipov.database.clinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentCriteriaRepository {

	List<Appointment> findByPatient_Id(Long patientId);

	List<Appointment> findByDoctor_Id(Long doctorId);

	@Query("""
        SELECT a
        FROM Appointment a
        JOIN FETCH a.doctor
        JOIN FETCH a.patient
        WHERE a.id = :id
    """)
	Optional<Appointment> findByIdWithDetails(@Param("id") Long id);
}