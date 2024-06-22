package in.java.RestController;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.java.Entity.Employee;
import in.java.ServiceImpl.EmployeeService;
import in.java.ServiceImpl.Exception.EmployeeNotFoundException;

@RestController
@RequestMapping("/employee")
public class EmployeerestController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmployeeService service;
	
	/*
	     We can use any mapping like get,post,put,patch,delete to create new resources
	     But My Opinion to use only post to create new record
	 */
	
	//@GetMapping("/empsave")
	//@PatchMapping("/empsave")
	//@PutMapping("/empsave")
	//@DeleteMapping("/empsave")
	@PostMapping("/empsave")
	public ResponseEntity<String> saveEmployeeDetails(@RequestBody Employee employee){
		
		ResponseEntity<String> resp = null;
		
		try {
			
			Integer EmployeeId = service.saveEmployee(employee);
			String message = "Employee "+EmployeeId+" Saved";
			
			 resp = new ResponseEntity<String>(message, HttpStatus.OK);
			
		}catch(EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return resp;
	}
	
	
	/* 
        If @RequestBody annotation is not there then application will execute but that record willl store as NULL
        If PathVaiable annotation is miising then that exception with message will show
        View  = java.lang.IllegalArgumentException: The given id must not be null,
    */
	
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getoneEmployeeData(@PathVariable Integer id){
		
		ResponseEntity<?> resp = null;
		
		try {
			Optional<Employee> oneEMployee = service.getOneEMployee(id);
			if(oneEMployee.isPresent()) {
				Employee employee = oneEMployee.get();
				String str = "Employee Geted "+employee+" Successfyully";
				
				resp = new ResponseEntity<>(str, HttpStatus.OK);
			}else {
				resp = new ResponseEntity<>("Unable to fetch employee", HttpStatus.BAD_GATEWAY);
			}
		}catch(EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return resp;
	}
	
	
	
	/*
        We can use any mapping like get,post,put,patch,delete to fetch all record List Of Record
        But My Opinion to use only GetMapping to Fetch all record 
    */
	

	//@PostMapping("/getAll")
	//@PutMapping("/getAll")
	//@PatchMapping("/getAll")
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getAllEmployeeData(){
		ResponseEntity<List<Employee>> resp = null;
		
		try {
			List<Employee> allEmployee = service.getAllEmployee();
			if(allEmployee!=null && !allEmployee.isEmpty()) {
				
//				allEmployee.sort(
//						
//						(o1, o2) -> o1.getName().compareTo(o2.getName())
//						);
				resp = new ResponseEntity<List<Employee>>(allEmployee, HttpStatus.CREATED);
			}else {
				resp = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
			}
		}catch(EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return resp;
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletedEmployee(@PathVariable Integer id){
		ResponseEntity<String> resp = null;
		
		boolean employeeExists = service.employeeExists(id);
		
		try {
		if(employeeExists) {
			service.deleteEmployee(id);
			resp =new ResponseEntity<String>("Employee id "+id+"Deleted Successully", HttpStatus.ACCEPTED);
		}else {
			resp = new ResponseEntity<String>("Unable to delete", HttpStatus.BAD_REQUEST);
		}
		}catch(EmployeeNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	
		return resp;
		
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee emp, @PathVariable Integer id){
		
		Employee updateEmployee = service.updateEmployee(emp, id);
		if(updateEmployee!=null) {
			return new ResponseEntity<Employee>(updateEmployee, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<Employee>(updateEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
