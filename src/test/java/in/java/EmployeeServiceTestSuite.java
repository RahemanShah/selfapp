package in.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import in.java.Entity.Employee;
import in.java.ServiceImpl.EmployeeService;
import in.java.repo.EmployeeRepo;

@SpringBootTest
public class EmployeeServiceTestSuite {

	@Mock
	EmployeeRepo empRepo;
	
	

	@InjectMocks
	EmployeeService service;

	@Test
	public void shouldReturnEmployeeListWhenGetAllEmployeeIsCalled() {

		List<Employee> myEmployees = new ArrayList<>();

		myEmployees.add(new Employee(9, "Radha", 3.0, "rd@gmail.com", "Java", "Pune"));
		myEmployees.add(new Employee(10, "Pavan", 2.9, "pv@gmail.com", "MuleSoft", "Bangalore"));

		when(empRepo.findAll()).thenReturn(myEmployees);

		assertEquals(2, service.getAllEmployee().size());
	}

	@Test
	@Order(2)
	public void Test_getEmployeeById() {

		int employeeId = 1;
		Employee getEmpId = new Employee();
		getEmpId.setId(employeeId);

		when(empRepo.findById(employeeId)).thenReturn(Optional.of(getEmpId));

		Optional<Employee> oneEMployee = service.getOneEMployee(employeeId);

		oneEMployee.ifPresent(emp -> {

			assertTrue(oneEMployee.isPresent());
			assertEquals(employeeId, emp.getId());
		});

//        assertTrue(oneEMployee.isPresent());
//        assertEquals(employeeId, service.getOneEMployee(oneEMployee.get().getId()));

	}

	@Test
	@Order(3)
	void testDeleteEmployee() {
		Integer employeeId = 1;

		try {
			service.deleteEmployee(employeeId);

		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
		verify(empRepo, times(1)).deleteById(employeeId);
	}

	@Test
	@Order(4)
	void Test_CreateEmployee() {

		Employee employee = new Employee();
		employee.setId(1);

		when(empRepo.save(any(Employee.class))).thenReturn(employee);

		assertEquals(employee.getId(), service.saveEmployee(employee));
	}

	@Test
	@Order(5)
	void Test_UpdateEmployee() {

		int empId = 1;
		Employee empUpdate = new Employee();
		empUpdate.setId(empId);

		when(empRepo.findById(empId)).thenReturn(Optional.of(empUpdate));
		when(empRepo.save(any(Employee.class))).thenAnswer(emp -> emp.getArgument(0));
	
		ArrayList<Employee> updateEmp = new ArrayList<>();
		updateEmp.forEach(emp ->{
			
			emp.setCourse(empUpdate.getCourse());
			emp.setFee(empUpdate.getFee());
			emp.setGmail(empUpdate.getGmail());
			emp.setName(empUpdate.getName());
			emp.setAddress(empUpdate.getAddress());
			
			assertEquals(updateEmp, service.updateEmployee(emp, empId));
		});

		

	}
}
