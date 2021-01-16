package test.challenge.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class DepartmentRequest {
	
	private String id;
	
	@NotEmpty(message = "Name cannot be empty.")
	@Size(min=5)
	private String name;
	
	@NotEmpty(message = "Employee cannot be empty.")
	@Size(min=30)
	private String idEmployee;

}
