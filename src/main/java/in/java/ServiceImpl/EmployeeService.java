package in.java.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.java.Entity.Employee;
import in.java.repo.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
    private EmployeeRepo empRepo;


	public Integer saveEmployee(Employee employee) {
		
	     return empRepo.save(employee).getId();
	}

	public Optional<Employee> getOneEMployee(Integer id) {
		
		 Optional<Employee> findId = empRepo.findById(id);
		 return findId;
	}


	public List<Employee> getAllEmployee() {
		
	     List<Employee> findAll = empRepo.findAll();
	     return findAll;
	}


	public Employee updateEmployee(Employee employee, Integer id) {
		
		Optional<Employee> existingEmployee = empRepo.findById(employee.getId());

	    if (existingEmployee.isPresent()) {
	    	
	        Employee updatedEmployee = existingEmployee.get();

	       
	        updatedEmployee.setName(employee.getName());
	        updatedEmployee.setFee(employee.getFee());
	        updatedEmployee.setGmail(employee.getGmail());
	        updatedEmployee.setCourse(employee.getCourse());
	        updatedEmployee.setAddress(employee.getAddress());

	        return empRepo.save(updatedEmployee);
	        
	    } else {
	        throw new RuntimeException("Employee not found with id: " + employee.getId());
	    }
	}


	public void deleteEmployee(Integer id) {
		 Optional<Employee> findById = empRepo.findById(id);
		 if(findById.isPresent()) {
			  
			 
		 }
	}


	public boolean employeeExists(Integer id) {
		
		boolean existsById = empRepo.existsById(id);
		return existsById;
	}

}
