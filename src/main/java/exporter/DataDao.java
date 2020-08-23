package exporter;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DataDao {
	List<ImportantData> getUsersActiveDuringLastHour();
	List<ImportantData> getUnfinishedUsersAndSteps();
	Iterable<String> getTopFiveForms();
	void populateTable(InputStream is) throws IOException;
}
