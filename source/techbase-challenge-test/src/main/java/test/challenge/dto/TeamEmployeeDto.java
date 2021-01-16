package test.challenge.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TeamEmployeeDto {
	
	private String id;
	
	private String name;
	
	private String idDepartment;
	
	private String nameDepartment;
	
	private String idTeamLeader;
	
	private String nameTeamLeader;
	
	private Date updatedAt;

}
