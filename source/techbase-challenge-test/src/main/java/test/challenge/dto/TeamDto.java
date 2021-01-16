package test.challenge.dto;

import java.util.Date;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class TeamDto {
	
	private String id;
	
	private String name;
	
	private String idDepartment;
	
	private String nameDepartment;
	
	private String idTeamLeader;
	
	private String nameTeamLeader;
	
	private Date updatedAt;
	
}
