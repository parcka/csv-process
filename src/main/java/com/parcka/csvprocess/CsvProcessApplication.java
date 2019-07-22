package com.parcka.csvprocess;

import com.parcka.csvprocess.core.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class CsvProcessApplication implements CommandLineRunner {

	@Autowired
	CSVReader reader;

	public static void main(String[] args) {

//		SpringApplication springApplication =
//				new SpringApplicationBuilder()
//						.sources(CsvProcessApplication.class)
//						.web(WebApplicationType.NONE)
//						.build();
//
//		springApplication.run(args);

		SpringApplication.run(CsvProcessApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("Comenzando ejecucion....");
		reader.processFile();
	}
}
