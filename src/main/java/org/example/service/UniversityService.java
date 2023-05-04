package org.example.service;

import org.example.dao.DepartmentRepository;
import org.example.dao.LectorRepository;
import org.example.models.Degree;
import org.example.models.Department;
import org.example.models.Lector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityService {

    private final DepartmentRepository departmentRepository;

    private final LectorRepository lectorRepository;

    public UniversityService(DepartmentRepository departmentRepository, LectorRepository lectorRepository) {
        this.departmentRepository = departmentRepository;
        this.lectorRepository = lectorRepository;
    }

    public void addData() {
        Lector john = new Lector();
        Lector jane = new Lector();
        Lector jack = new Lector();
        Department math = new Department();
        Department phys = new Department();
        List<Department> listMath = new ArrayList<>();
        List<Department> listPhysic = new ArrayList<>();
        math.setId(1);
        math.setName("Mathematics");
        math.setHead(john);

        phys.setId(2);
        phys.setName("Physics");
        phys.setHead(jane);


        john.setId(1);
        john.setName("John");
        john.setDegree(Degree.PROFESSOR);
        listMath.add(math);
        john.setDepartments(listMath);
        john.setSalary(1000);

        jane.setId(2);
        jane.setName("Jane");
        jane.setDegree(Degree.ASSOCIATE_PROFESSOR);
        jane.setDepartments(listPhysic);
        jane.setSalary(2000);

        jack.setId(3);
        jack.setName("Jack");
        jack.setDegree(Degree.ASSISTANT);
        jack.setDepartments(listMath);
        jack.setSalary(2500);


        departmentRepository.save(math);
        departmentRepository.save(phys);
        lectorRepository.save(john);
        lectorRepository.save(jane);
        lectorRepository.save(jack);
    }


    // Command 1: Who is head of department {department_name}
    public String getDepartmentHead(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            Lector head = department.getHead();
            if (head != null) {
                return "Head of " + departmentName + " department is " + head.getName();
            }
            return "There is no head assigned to " + departmentName + " department";
        }
        return departmentName + " department not found";
    }

    // Command 2: Show {department_name} statistics.
    public String getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            long assistants = lectorRepository.countByDepartmentsAndDegree(department, Degree.ASSISTANT);
            long associateProfessors = lectorRepository.countByDepartmentsAndDegree(department, Degree.ASSOCIATE_PROFESSOR);
            long professors = lectorRepository.countByDepartmentsAndDegree(department, Degree.PROFESSOR);
            return "assistants - " + assistants + ". associate professors - " + associateProfessors + ". professors - " + professors;
        }
        return departmentName + " department not found";
    }

    // Command 3: Show the average salary for the department {department_name}.
    public String getDepartmentAverageSalary(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            double averageSalary = lectorRepository.getAverageSalaryByDepartment(department.getName());
            return "The average salary of " + departmentName + " is " + averageSalary;
        }
        return departmentName + " department not found";
    }

    // Command 4: Show count of employee for {department_name}.
    public String getDepartmentEmployeeCount(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            long employeeCount = lectorRepository.countByDepartments(department);
            return String.valueOf(employeeCount);
        }
        return departmentName + " department not found";
    }

    // Command 5: Global search by {template}.
    public String searchLectors(String template) {
        List<Lector> lectors = lectorRepository.findByNameContainingIgnoreCase(template);
        if (!lectors.isEmpty()) {
            return lectors.stream().map(Lector::getName).collect(Collectors.joining(", "));
        }
        return "No lectors found matching the template " + template;
    }
}

