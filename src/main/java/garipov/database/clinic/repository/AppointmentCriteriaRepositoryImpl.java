package garipov.database.clinic.repository;

import garipov.database.clinic.dto.request.AppointmentFilter;
import garipov.database.clinic.entity.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentCriteriaRepositoryImpl implements AppointmentCriteriaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Appointment> findByFilter(AppointmentFilter filter) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Appointment> query = cb.createQuery(Appointment.class);
		Root<Appointment> root = query.from(Appointment.class);

		List<Predicate> predicates = new ArrayList<>();

		if (filter.getPatientId() != null) {
			predicates.add(
					cb.equal(root.get("patient").get("id"), filter.getPatientId())
			);
		}

		if (filter.getDoctorId() != null) {
			predicates.add(
					cb.equal(root.get("doctor").get("id"), filter.getDoctorId())
			);
		}

		if (filter.getStatus() != null) {
			predicates.add(
					cb.equal(root.get("status"), filter.getStatus())
			);
		}

		query.select(root).where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(query).getResultList();
	}
}