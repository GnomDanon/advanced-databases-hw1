package garipov.database.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient extends BaseEntity {

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(nullable = false, unique = true)
	private String phone;

	@OneToOne(
			mappedBy = "patient",
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			orphanRemoval = true
	)
	private MedicalRecord medicalRecord;

	@OneToMany(
			mappedBy = "patient",
			fetch = FetchType.LAZY
	)
	private List<Appointment> appointments = new ArrayList<>();

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
		if (medicalRecord != null) {
			medicalRecord.setPatient(this);
		}
	}

	public void removeMedicalRecord() {
		MedicalRecord oldRecord = this.medicalRecord;
		this.medicalRecord = null;

		if (oldRecord != null) {
			oldRecord.setPatient(null);
		}
	}

	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
		appointment.setPatient(this);
	}
}