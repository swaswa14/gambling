package ph.cdo.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"ph.cdo.backend"})
public class BackEndApplication {

	Logger logger = LoggerFactory.getLogger(BackEndApplication.class);

	public static void main(String[] args) {


		SpringApplication app = new SpringApplication(BackEndApplication.class);

		app.setLogStartupInfo(false);
		app.run(args);
	}



}
