package in.java.ServiceImpl.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyCustomExceptionHandeler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<String> HandelEmployeeNotFountException(EmployeeNotFoundException e){
		
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
