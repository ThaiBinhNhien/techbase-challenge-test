package test.challenge.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import test.challenge.config.Constants;
import test.challenge.dto.EmployeeDto;
import test.challenge.dto.EmployeeTeamDto;
import test.challenge.entity.Employee;
import test.challenge.entity.Role;
import test.challenge.entity.Team;
import test.challenge.entity.User;
import test.challenge.repository.EmployeeRepository;
import test.challenge.repository.RoleRepository;
import test.challenge.repository.TeamRepository;
import test.challenge.repository.UserRepository;
import test.challenge.request.UserRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;
import test.challenge.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeRepository employeeRepository;
	private TeamRepository teamRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public EmployeeServiceImpl( EmployeeRepository employeeRepository,
			UserRepository userRepository, 
			RoleRepository roleRepository,
			TeamRepository teamRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.employeeRepository = employeeRepository;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.roleRepository = roleRepository;
		this.teamRepository = teamRepository;
	}


	@Override
	public SingleResponse<List<EmployeeDto>> getEmployeeDepartment() {
		SingleResponse<List<EmployeeDto>> singleResponse = new SingleResponse<List<EmployeeDto>>();
		singleResponse.setCode(Constants.SUCCESS_CODE);
		singleResponse.setSuccess(true);
		singleResponse.setData(employeeRepository.findEmployeeForDeparment());
		return singleResponse;
	}

	@Override
	public SingleResponse<List<EmployeeDto>> getEmployeeForTeam() {
		SingleResponse<List<EmployeeDto>> singleResponse = new SingleResponse<List<EmployeeDto>>();
		singleResponse.setCode(Constants.SUCCESS_CODE);
		singleResponse.setSuccess(true);
		singleResponse.setData(employeeRepository.findEmployeeForTeam());
		return singleResponse;
	}

	@Override
	public PagingResponse<List<EmployeeDto>> getAllEmployee(int page, int record, String search) {
		Page<EmployeeDto> employeeDtoPage = employeeRepository.findWithPagination(PageRequest.of(page, record, Sort.by("updatedAt").descending()));
		PagingResponse<List<EmployeeDto>> pagingResponse = new PagingResponse<>();
		pagingResponse.setTotal(employeeDtoPage.getTotalElements());
		pagingResponse.setData(employeeDtoPage.toList());
		pagingResponse.setCode(200);
		pagingResponse.setSuccess(true);
		return pagingResponse;
	}


	@Override
	@Transactional
	public SingleResponse<String> createEmployee(UserRequest userRequest) throws Exception{

		SingleResponse<String> singleResponse = new SingleResponse<String>();
		User user = userRepository.findByUserName(userRequest.getUserName());
		if(user == null) {
			

			Employee employee = Employee.builder()
					.fullName(userRequest.getFullName()).build();
			employee.setCreatedAt(new Date());
			employee.setUpdatedAt(new Date());
			
			Role userRole = roleRepository.findByRole(Constants.EMPLOYEE_ROLE);
			
			user = User.builder()
					.userName(userRequest.getUserName())
					.password(bCryptPasswordEncoder.encode(userRequest.getPassword()))
					.role(userRole)
					.employee(employee)
					.build();
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());

			employee.setUser(user);

			userRepository.save(user);
			singleResponse.setCode(Constants.SUCCESS_CODE);
			singleResponse.setMessage("Create success!");
			singleResponse.setSuccess(true);

		}
		else {
			singleResponse.setMessage("Employee has existed!");
		}
		return singleResponse;
	}


	@Override
	public SingleResponse<String> removeEmployee(String id) throws Exception {
		SingleResponse<String> response = new SingleResponse<String>();
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if(employeeOptional.isPresent()) {
			Employee employeeRemove = employeeOptional.get();
			if(!employeeRemove.getUser().getRole().getRole().contains(Constants.DIRECTOR_ROLE) && employeeRemove.getDepartment() == null && employeeRemove.getTeam() == null) {
				userRepository.delete(employeeRemove.getUser());
				response.setCode(Constants.SUCCESS_CODE);
				response.setMessage("Remove success!");
				response.setSuccess(true);
			}
			else {
				response.setMessage("Please transfer Employee's role to another employee!");
			}
		}
		else {
			response.setMessage("Employee has existed!");
		}
		
		return response;
	}


	@Override
	public SingleResponse<List<EmployeeDto>> getListEmployee() {
		SingleResponse<List<EmployeeDto>> singleResponse = new SingleResponse<List<EmployeeDto>>();
		singleResponse.setCode(Constants.SUCCESS_CODE);
		singleResponse.setSuccess(true);
		singleResponse.setData(employeeRepository.findListEmployee());
		return singleResponse;
	}


	@Override
	public SingleResponse<List<EmployeeTeamDto>> getEmployeeJoinedTeam(String id) {
		SingleResponse<List<EmployeeTeamDto>>response = new SingleResponse<List<EmployeeTeamDto>>();
		Optional<Team> teamOptional = teamRepository.findById(id);
		if(teamOptional.isPresent()) {
			List<EmployeeTeamDto> lstEmployeeDto = new ArrayList<EmployeeTeamDto>();
			lstEmployeeDto = teamOptional.get().getTeamMember().stream().map(em-> {
				EmployeeTeamDto employeeDto = new EmployeeTeamDto();
				employeeDto.setFullName(em.getFullName());
				employeeDto.setId(em.getId());
				employeeDto.setUserId(em.getUser().getId());
				employeeDto.setUserName(em.getUser().getUserName());
				employeeDto.setRole(em.getUser().getRole().getRole());
				employeeDto.setUpdatedAt(em.getUpdatedAt());
				return employeeDto;
			}).collect(Collectors.toList());
			
			response.setCode(Constants.SUCCESS_CODE);
			response.setSuccess(true);
			response.setData(lstEmployeeDto);
		}
		else {
			response.setMessage("Team not found");
		}
		return response;
	}

}
