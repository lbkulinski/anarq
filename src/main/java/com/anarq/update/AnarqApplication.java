package com.anarq.update;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * An application for the AnarQ system.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version March 8, 2020
 */
@SpringBootApplication
public class AnarqApplication {
    /**
     * Runs the application of the AnarQ system.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AnarqApplication.class, args);
    } //main
}