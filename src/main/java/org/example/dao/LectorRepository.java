package DAO;

import models.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectorRepository extends JpaRepository<Lector, Long> {
    List<Lector> findByDepartmentsName(String name);
    @Query("SELECT AVG(l.salary) FROM Lector l JOIN l.departments d WHERE d.name = :name")
    Double averageSalaryByDepartmentsName(String name);
}
