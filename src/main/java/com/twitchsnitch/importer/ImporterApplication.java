package com.twitchsnitch.importer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ImporterApplication {

	public static void main(String[] args){
		SpringApplication.run(ImporterApplication.class, args);

	}

}

