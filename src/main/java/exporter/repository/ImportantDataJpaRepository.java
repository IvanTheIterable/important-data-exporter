package exporter.repository;

import exporter.model.ImportantData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportantDataJpaRepository extends JpaRepository<ImportantData, Long> {
	List<ImportantData> findByTsGreaterThan(long ts);
	List<ImportantData> findBySubtypeNot(String subtype);
}
