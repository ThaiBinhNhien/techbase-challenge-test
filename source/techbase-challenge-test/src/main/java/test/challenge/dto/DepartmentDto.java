package test.challenge.dto;

import java.util.Date;

import groovy.transform.builder.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
public class DepartmentDto {
	
	private String id;
	
	private String name;
	
	private String idEmployee;
	
	private String nameEmployee;
	
	private Date updatedAt;

}
