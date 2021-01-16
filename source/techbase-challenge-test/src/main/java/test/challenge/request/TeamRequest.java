package test.challenge.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TeamRequest {
	
	private String id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String idDepartment;
	
	@NotEmpty
	private String idTeamLeader;
	
}
