package test.challenge.dto;

import java.util.Date;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class EmployeeDto {
	
	private String id;
	
	private String fullName;
	
	private String userId;
	
	private String userName;
	
	private String role;
	
	private Date updatedAt;

}
