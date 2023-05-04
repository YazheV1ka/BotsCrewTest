package org.example.dao;

import org.example.models.Degree;
import org.example.models.Department;
import org.example.models.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    long countByDepartments(Department department);

    long countByDepartmentsAndDegree(Department department, Degree degree);

    @Query("SELECT AVG(l.salary) FROM Lector l JOIN l.departments d WHERE d.name = :departmentName")
    Double getAverageSalaryByDepartment(@Param("departmentName") String departmentName);


    List<Lector> findByNameContainingIgnoreCase(String name);

}
