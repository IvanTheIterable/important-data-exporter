package exporter;

import exporter.model.ImportantData;
import exporter.repository.ImportantDataJpaRepository;
import exporter.repository.SpecialCaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExporterTest {
    @Autowired
    private ImportantDataJpaRepository repository;
    @Autowired
    private SpecialCaseRepository specialCaseRepository;

    @Test
    public void applicationTest() {
        List<ImportantData> result = repository.findByTsGreaterThan(0);
        assertEquals(56817, result.size());
        result = repository.findByTsGreaterThan(1499763599);
        assertEquals(54408, result.size());

        result = repository.findBySubtypeNot("send");
        assertEquals(50663, result.size());

        List<String> topForms = specialCaseRepository.getTopFiveForms();
        assertEquals(5, topForms.size());
    }

}
