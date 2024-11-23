package ssf.day13_ws;

import java.io.*;
import java.util.logging.Logger;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day13WsApplication {

	private static final Logger logger = Logger.getLogger(Day13WsApplication.class.getName());
	public static String dataDir = "";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Day13WsApplication.class);
		ApplicationArguments cliOpts = new DefaultApplicationArguments(args);

		if(cliOpts.containsOption("dataDir")) {
			dataDir = "C://" + cliOpts.getOptionValues("dataDir").get(0);
			File data = new File(dataDir);
			if(!data.exists()) {
				data.mkdirs();
				logger.info("Directory " + data.getAbsolutePath() +" has been created: " + data.exists());
			}
				
		} else {
			logger.severe("Please include --dataDir option");
			System.exit(-1);
		}
		app.run(args);
	}
}
