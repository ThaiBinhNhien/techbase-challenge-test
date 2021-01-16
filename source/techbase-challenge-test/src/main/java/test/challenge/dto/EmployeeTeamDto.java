package test.challenge.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeTeamDto {
	private String id;

	private String fullName;

	private String userId;

	private String userName;

	private String role;

	private Date updatedAt;

}
