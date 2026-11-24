package garipov.database.clinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@SequenceGenerator(
			name = "entity_seq",
			sequenceName = "entity_seq",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "entity_seq"
	)
	private Long id;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;

		if (Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}

		BaseEntity that = (BaseEntity) o;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Hibernate.getClass(this).hashCode();
	}
}