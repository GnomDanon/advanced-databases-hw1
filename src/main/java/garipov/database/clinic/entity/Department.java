package garipov.database.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String name;

	private Integer floor;

	private String description;

	@OneToMany(
			mappedBy = "department",
			fetch = FetchType.LAZY
	)
	private List<Doctor> doctors = new ArrayList<>();

	public void addDoctor(Doctor doctor) {
		if (doctor.getDepartment() != null) {
			doctor.getDepartment().removeDoctor(doctor);
		}

		doctors.add(doctor);
		doctor.setDepartment(this);
	}

	public void removeDoctor(Doctor doctor) {
		doctors.remove(doctor);
		doctor.setDepartment(null);
	}
}