package org.example;

import org.example.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private final UniversityService universityService;


    @Autowired
    public Main(UniversityService universityService) {
        this.universityService = universityService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Override
    public void run(String... args) {
        universityService.addData();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a command or 'q' to quit:");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                break;
            }

            String[] tokens = input.split("\\s+", 8);
            String command = tokens[0];
            String departmentName;
            String template = null;

            if (tokens.length >= 4 && tokens[2].equals("by")) {
                template = tokens[3];
            }

            String result;
            switch (command) {
                case "Who":
                    departmentName = tokens[tokens.length - 1];
                    result = universityService.getDepartmentHead(departmentName);
                    break;
                case "Show":
                    if (tokens[2].equals("statistics.")) {
                        departmentName = tokens[tokens.length - 2];
                        result = universityService.getDepartmentStatistics(departmentName);
                    } else if (tokens[1].equals("the") && tokens[2].equals("average") && tokens[3].equals("salary")) {
                        departmentName = tokens[tokens.length - 1];
                        result = universityService.getDepartmentAverageSalary(departmentName);
                    } else if (tokens[1].equals("count") && tokens[2].equals("of") && tokens[3].equals("employee")) {
                        departmentName = tokens[tokens.length - 1];
                        result = universityService.getDepartmentEmployeeCount(departmentName);
                    } else {
                        result = "Invalid command.";
                    }
                    break;
                case "Global":
                    result = universityService.searchLectors(template);
                    break;
                default:
                    result = "Invalid command.";
                    break;
            }
            System.out.println(result);
        }
    }
}