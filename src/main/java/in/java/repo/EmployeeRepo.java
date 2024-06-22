package in.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.java.Entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}
