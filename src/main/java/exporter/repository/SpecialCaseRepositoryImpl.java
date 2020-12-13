package exporter.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpecialCaseRepositoryImpl implements SpecialCaseRepository {

	private final JdbcTemplate jdbc;

	@Override
	public List<String> getTopFiveForms() {
		String sql = "select (case when formid = '' then '<empty>' else formid end) from Important_Data group by formid order by count(formid) desc limit 5";
		return jdbc.query(sql, (rs, rowNum)-> rs.getString("formid"));
	}
}
