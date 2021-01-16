package test.challenge.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserRequest {
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String fullName;
	
	@NotEmpty
	private String password;
	
}
