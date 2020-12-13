package exporter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
public class ImportantData {
    private static final char DELIMITER = ';';
    private static final String[] HEADERS = {"ssoid", "ts", "grp", "type", "subtype", "url", "orgid", "formid", "code", "ltpa", "sudirresponse", "ymdh"};

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    private long id;
    private String ssoid;
    private long ts;
    private String grp;
    private String type;
    private String subtype;
    private String url;
    private String orgid;
    private String formid;
    private String code;
    private String ltpa;
    private String sudirresponse;
    private String ymdh;

    public ImportantData(CSVRecord x) {
        this.ssoid = x.get("ssoid");
        this.ts = Long.parseLong(x.get("ts"));
        this.grp = x.get("grp");
        this.type = x.get("type");
        this.subtype = x.get("subtype");
        this.url = x.get("url");
        this.orgid = x.get("orgid");
        this.formid = x.get("formid");
        this.code = x.get("code");
        this.ltpa = x.get("ltpa");
        this.sudirresponse = x.get("sudirresponse");
        this.ymdh = x.get("ymdh");
    }

    public static List<CSVRecord> getRecords(InputStream is) throws IOException {
        return CSVFormat.EXCEL.withDelimiter(DELIMITER)
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(new InputStreamReader(is))
                .getRecords();
    }
}
