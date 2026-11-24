package garipov.database.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends BaseEntity {

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String specialization;

	private Integer experienceYears;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	@OneToMany(
			mappedBy = "doctor",
			fetch = FetchType.LAZY
	)
	private List<Appointment> appointments = new ArrayList<>();

	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
		appointment.setDoctor(this);
	}
}