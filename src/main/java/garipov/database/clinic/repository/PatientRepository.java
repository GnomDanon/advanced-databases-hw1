package garipov.database.clinic.repository;

import garipov.database.clinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	List<Patient> findByLastNameContainingIgnoreCase(String lastName);

	@Query(
			value = "SELECT * FROM patients WHERE phone = :phone",
			nativeQuery = true
	)
	Optional<Patient> findByPhoneNative(@Param("phone") String phone);
}