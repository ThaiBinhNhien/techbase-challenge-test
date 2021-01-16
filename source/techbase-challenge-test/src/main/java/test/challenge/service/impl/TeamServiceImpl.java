package test.challenge.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import test.challenge.config.Constants;
import test.challenge.dto.TeamDto;
import test.challenge.dto.TeamEmployeeDto;
import test.challenge.entity.Department;
import test.challenge.entity.Employee;
import test.challenge.entity.Role;
import test.challenge.entity.Team;
import test.challenge.repository.DepartmentRepository;
import test.challenge.repository.EmployeeRepository;
import test.challenge.repository.RoleRepository;
import test.challenge.repository.TeamRepository;
import test.challenge.request.TeamRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;
import test.challenge.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

	private TeamRepository teamRepository;
	private EmployeeRepository employeeRepository;
	private DepartmentRepository departmentRepository;
	private RoleRepository roleRepository;

	@Autowired
	public TeamServiceImpl(TeamRepository teamRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository) {
		this.teamRepository = teamRepository;
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public PagingResponse<List<TeamDto>> getAllTeam(int page, int record, String search) {
		Page<TeamDto> teamDtoPage = teamRepository.findWithPagination(PageRequest.of(page, record, Sort.by("updatedAt").descending()));
		PagingResponse<List<TeamDto>> pagingResponse = new PagingResponse<>();
		pagingResponse.setTotal(teamDtoPage.getTotalElements());
		pagingResponse.setData(teamDtoPage.toList());
		pagingResponse.setCode(Constants.SUCCESS_CODE);
		pagingResponse.setSuccess(true);
		return pagingResponse;
	}

	@Override
	public SingleResponse<TeamDto> getDetail(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleResponse<String> createTeam(TeamRequest teamRequest) throws Exception {
		SingleResponse<String> singleResponse = new SingleResponse<String>();
		Optional<Employee> employeeOptional = employeeRepository.findById(teamRequest.getIdTeamLeader());

		if(employeeOptional.isPresent()) {

			Optional<Department> departmentOptional = departmentRepository.findById(teamRequest.getIdDepartment());
			if(departmentOptional.isPresent())
			{

				Role role = roleRepository.findByRole(Constants.TEAMLEADER_ROLE);
				Employee employee = employeeOptional.get();
				employee.getUser().setRole(role);
				
				Team team = Team.builder()
						.department(departmentOptional.get())
						.name(teamRequest.getName())
						.teamLeader(employee)
						.build();

				team.setCreatedAt(new Date());
				team.setUpdatedAt(new Date());

				teamRepository.save(team);
				singleResponse.setCode(Constants.SUCCESS_CODE);
				singleResponse.setMessage("Create success!");
				singleResponse.setSuccess(true);
			}
			else {
				singleResponse.setMessage("Department not found!");
			}
		}
		else {
			singleResponse.setMessage("Employee not found!");
		}
		return singleResponse;
	}

	@Override
	public SingleResponse<String> updateTeam(TeamRequest teamRequest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public SingleResponse<String> removeTeam(String id) throws Exception {
		SingleResponse<String> response = new SingleResponse<String>();
		Optional<Team> teamOptional = teamRepository.findById(id);
		if(teamOptional.isPresent()) {
			
			Team teamRemove = teamOptional.get();
			Role role = roleRepository.findByRole(Constants.EMPLOYEE_ROLE);
			Employee employee = teamRemove.getTeamLeader();
			employee.getUser().setRole(role);
			employee.setTeam(null);
			
			employeeRepository.save(employee);
			
			teamRemove.setTeamLeader(null);
			teamRepository.delete(teamRemove);
			response.setCode(Constants.SUCCESS_CODE);
			response.setMessage("Remove success!");
			response.setSuccess(true);
		}
		else {
			response.setMessage("Employee has existed!");
		}

		return response;
	}

	@Override
	public SingleResponse<List<TeamDto>> getAllTeam() {
		SingleResponse<List<TeamDto>> response = new SingleResponse<List<TeamDto>>();
		response.setData(teamRepository.findAllTeam());
		response.setCode(Constants.SUCCESS_CODE);
		response.setSuccess(true);
		return response;
	}

	@Override
	public SingleResponse<List<TeamEmployeeDto>> getListTeamByEmployee(String id) {
		SingleResponse<List<TeamEmployeeDto>> response = new SingleResponse<List<TeamEmployeeDto>>();
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if(employeeOptional.isPresent()) {
			List<TeamEmployeeDto> lstTeamEmployeeDto = new ArrayList<TeamEmployeeDto>();
			lstTeamEmployeeDto = employeeOptional.get().getJoinedTeams().stream().map(t -> {
				TeamEmployeeDto teamEmployeeDto = new TeamEmployeeDto();
				teamEmployeeDto.setId(t.getId());
				teamEmployeeDto.setIdDepartment(t.getDepartment().getId());
				teamEmployeeDto.setNameDepartment(t.getDepartment().getName());
				teamEmployeeDto.setIdTeamLeader(t.getTeamLeader().getId());
				teamEmployeeDto.setNameTeamLeader(t.getTeamLeader().getFullName());
				teamEmployeeDto.setUpdatedAt(t.getUpdatedAt());
				return teamEmployeeDto;
			}).collect(Collectors.toList());
			
			response.setCode(Constants.SUCCESS_CODE);
			response.setSuccess(true);
			response.setData(lstTeamEmployeeDto);
		}
		else {
			response.setMessage("Team not found");
		}
		return response;
	}

	@Override
	public SingleResponse<String> addEmployeeTeam(List<String> ids, String teamid) throws Exception {
		SingleResponse<String> response = new SingleResponse<String>();
		List<Employee> lstEmployee = employeeRepository.findAllById(ids);
		if(lstEmployee.size()>0) {
			Optional<Team> teamOptional = teamRepository.findById(teamid);
			if(teamOptional.isPresent()) {
				Team teamAdd = teamOptional.get();
				lstEmployee.addAll(teamAdd.getTeamMember());
				Set<Employee> setEmployee = lstEmployee.stream().collect(Collectors.toSet());
				teamAdd.setTeamMember(setEmployee);
				teamRepository.save(teamAdd);
				response.setCode(Constants.SUCCESS_CODE);
				response.setSuccess(true);
				response.setMessage("Add member to team is success");
			}
			else {
				response.setMessage("Team not found");
			}
		}
		else {
			response.setMessage("Employee not found");
		}
		
		return response;
	}

}
