package exporter;


import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {
	private static final String RESOURCE = "static/test_case.csv";
	private final DataDaoImpl rep;
	
	@Autowired
	public ReportController(DataDaoImpl rep) {
		this.rep = rep;
	}
	
	@ResponseBody
	@PostConstruct
	@GetMapping("/upload")
	String upload() throws IOException {
		try (InputStream in = getClass().getClassLoader().getResourceAsStream(RESOURCE)) {
			rep.populateTable(in);
	    }
		return "Data uploaded";
	}
	
	@GetMapping("/report1")
	String report1(Model model) {
		model.addAttribute("users", rep.getUsersActiveDuringLastHour());
		return "report1";
	}
	
	@GetMapping("/report2")
	String report2(Model model) {
		model.addAttribute("users", rep.getUnfinishedUsersAndSteps());
		return "report2";
	}
	
	@GetMapping("/report3")
	String report3(Model model) {
		model.addAttribute("forms", rep.getTopFiveForms());
		return "report3";
	}
	
}
