package exporter;


import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataDaoImpl implements DataDao {

	@Autowired
	private EntityManager em;
	
	@Autowired
	private JdbcTemplate jdbc;

	private Session getSession() {
		return em.unwrap(Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ImportantData> getUsersActiveDuringLastHour() {
		Session session = getSession();
		String hql = "from ImportantData u where u.ts > ";
		long timeCurrent = Instant.now().getEpochSecond() - 3600;
		List<ImportantData> userList = session.createQuery(hql + timeCurrent).list();
		session.close();
		return userList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ImportantData> getUnfinishedUsersAndSteps() {
		Session session = getSession();
		String hql = "from ImportantData u where u.subtype <> 'send'";
		List<ImportantData> userList = session.createQuery(hql).list();
		session.close();
		return userList;
	}

	@Override
	public Iterable<String> getTopFiveForms() {
		String sql = "select (case when formid = '' then '<empty>' else formid end) from Important_Data group by formid order by count(formid) desc limit 5";
		return jdbc.query(sql, (rs, rowNum)-> rs.getString("formid"));
	}

	@Override
	@Transactional
	public void populateTable(InputStream is) throws IOException {
		Session session = getSession();
		for(CSVRecord record : ImportantData.getRecords(is))
			session.save(new ImportantData(record));
		session.close();
	}

}
