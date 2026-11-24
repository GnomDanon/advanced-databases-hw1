package garipov.database.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "medical_records")
@Getter
@Setter
@NoArgsConstructor
public class MedicalRecord extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String number;

	@Column(nullable = false)
	private LocalDate createdAt;

	@Column(columnDefinition = "TEXT")
	private String medicalHistory;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "patient_id",
			nullable = false,
			unique = true
	)
	private Patient patient;
}