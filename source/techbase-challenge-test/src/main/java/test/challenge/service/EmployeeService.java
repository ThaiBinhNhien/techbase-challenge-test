package test.challenge.service;

import java.util.List;

import test.challenge.dto.EmployeeDto;
import test.challenge.dto.EmployeeTeamDto;
import test.challenge.request.UserRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;

public interface EmployeeService {
	
	PagingResponse<List<EmployeeDto>>  getAllEmployee(int page, int record, String search);
	
	SingleResponse<List<EmployeeDto>> getListEmployee();
	
	SingleResponse<List<EmployeeDto>> getEmployeeDepartment();
	
	SingleResponse<List<EmployeeDto>> getEmployeeForTeam();
	
	SingleResponse<List<EmployeeTeamDto>> getEmployeeJoinedTeam(String id);
	
	SingleResponse<String> createEmployee(UserRequest userRequest) throws Exception;
	
	SingleResponse<String> removeEmployee(String id) throws Exception;

}
