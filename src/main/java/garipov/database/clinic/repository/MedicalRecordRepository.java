package garipov.database.clinic.repository;

import garipov.database.clinic.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

	Optional<MedicalRecord> findByNumber(String number);

	Optional<MedicalRecord> findByPatient_Id(Long patientId);
}