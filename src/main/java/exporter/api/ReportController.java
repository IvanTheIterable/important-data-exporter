package exporter.api;

import exporter.model.ImportantData;
import exporter.repository.ImportantDataJpaRepository;
import exporter.repository.SpecialCaseRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {
	private final SpecialCaseRepositoryImpl specialCaseRepository;
	private final ImportantDataJpaRepository repository;

	@Value("classpath:static/test_case.csv")
	private Resource testCase;

	@ResponseBody
	@PostConstruct
	@GetMapping("/upload")
	String upload() throws IOException {
		try (InputStream is = testCase.getInputStream()) {
			repository.saveAll(ImportantData.getRecords(is).stream().map(ImportantData::new).collect(Collectors.toList()));
	    }
		return "Data uploaded";
	}
	
	@GetMapping("/report1")
	String report1(Model model) {
		model.addAttribute("users", repository.findByTsGreaterThan(Instant.now().getEpochSecond() - 3600));
		return "report1";
	}
	
	@GetMapping("/report2")
	String report2(Model model) {
		model.addAttribute("users", repository.findBySubtypeNot("send"));
		return "report2";
	}
	
	@GetMapping("/report3")
	String report3(Model model) {
		model.addAttribute("forms", specialCaseRepository.getTopFiveForms());
		return "report3";
	}
	
}
