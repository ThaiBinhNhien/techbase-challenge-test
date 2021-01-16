package test.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import test.challenge.dto.DepartmentDto;
import test.challenge.dto.EmployeeDto;
import test.challenge.dto.EmployeeTeamDto;
import test.challenge.dto.TeamDto;
import test.challenge.dto.TeamEmployeeDto;
import test.challenge.response.SingleResponse;
import test.challenge.service.DepartmentService;
import test.challenge.service.EmployeeService;
import test.challenge.service.TeamService;

@RestController
public class ApiController {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping(value="/api/v1/department/getall")
	public SingleResponse<List<DepartmentDto>> getAllDepartment(){
		SingleResponse<List<DepartmentDto>> response = departmentService.getListDepartment();
		return response;
	}
	
	@GetMapping(value="/api/v1/teams/getall")
	public SingleResponse<List<TeamDto>> getAllTeam(){
		SingleResponse<List<TeamDto>> response = teamService.getAllTeam();
		return response;
	}
	
	@GetMapping(value="/api/v1/teams/getbyemployee")
	public SingleResponse<List<TeamEmployeeDto>> getTeamsEmployeeJoined(String id){
		SingleResponse<List<TeamEmployeeDto>> response = teamService.getListTeamByEmployee(id);
		return response;
	}
	
	@GetMapping(value="/api/v1/employees/getall")
	public SingleResponse<List<EmployeeDto>> getAllEmployee(){
		SingleResponse<List<EmployeeDto>> response = employeeService.getListEmployee();
		return response;
	}
	
	@GetMapping(value="/api/v1/employees/getbyteam")
	public SingleResponse<List<EmployeeTeamDto>> getEmployeesByTeam(String id){
		SingleResponse<List<EmployeeTeamDto>> response = employeeService.getEmployeeJoinedTeam(id);
		return response;
	}

}
