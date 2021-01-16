package test.challenge.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import test.challenge.config.Constants;
import test.challenge.dto.DepartmentDto;
import test.challenge.entity.Department;
import test.challenge.entity.Employee;
import test.challenge.entity.Role;
import test.challenge.repository.DepartmentRepository;
import test.challenge.repository.EmployeeRepository;
import test.challenge.repository.RoleRepository;
import test.challenge.request.DepartmentRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;
import test.challenge.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	private DepartmentRepository departmentRepository;
	private EmployeeRepository employeeRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, RoleRepository roleRepository) {
		// TODO Auto-generated constructor stub
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public PagingResponse<List<DepartmentDto>> getAllDepartment(int page, int record, String search) {
		Page<DepartmentDto> departmentdtDtoPage = departmentRepository.findWithPagination(PageRequest.of(page, record, Sort.by("updatedAt").descending()));
        PagingResponse<List<DepartmentDto>> pagingResponse = new PagingResponse<>();
        pagingResponse.setTotal(departmentdtDtoPage.getTotalElements());
        pagingResponse.setData(departmentdtDtoPage.toList());
        pagingResponse.setCode(200);
        pagingResponse.setSuccess(true);
        return pagingResponse;
	}

	@Override
	public SingleResponse<DepartmentDto> getDetailDepartment(String id) {
		SingleResponse<DepartmentDto> singleResponse = new SingleResponse<DepartmentDto>();
		Optional<DepartmentDto> departmentOptional = departmentRepository.findDeparmentById(id);
		if(departmentOptional.isPresent()) {
			
			singleResponse.setCode(Constants.SUCCESS_CODE);
			singleResponse.setData(departmentOptional.get());
			singleResponse.setMessage("Get department success!");
			singleResponse.setSuccess(true);
		}
		else {
			singleResponse.setMessage("Department not found");
		}
		return singleResponse;
	}

	@Override
	@Transactional
	public SingleResponse<String> createDepartment(DepartmentRequest departmentRequest) {
		SingleResponse<String> singleResponse = new SingleResponse<String>();
		Optional<Employee> employeeOptional = employeeRepository.findById(departmentRequest.getIdEmployee());
		if( employeeOptional.isPresent()) {
			
			Role role = roleRepository.findByRole(Constants.MANAGER_ROLE);
			Employee employee = employeeOptional.get();
			employee.getUser().setRole(role);
			
			Department newDepartment = Department.builder()
					.headDepartment(employee)
					.name(departmentRequest.getName())
					.build();
			newDepartment.setCreatedAt(new Date());
			newDepartment.setUpdatedAt(new Date());
			
			departmentRepository.save(newDepartment);
			
			singleResponse.setCode(Constants.SUCCESS_CODE);
			singleResponse.setMessage("Create success!");
			singleResponse.setSuccess(true);
		}
		else {
			singleResponse.setMessage("Employee not found!");
		}
				
		return singleResponse;
	}

	@Override
	@Transactional
	public SingleResponse<String> removeDepartment(String id) {
		SingleResponse<String> singleResponse = new SingleResponse<String>();
		Optional<Department> departmentOptional = departmentRepository.findById(id);
		if(departmentOptional.isPresent()) {
			Department departmentRemove = departmentOptional.get();
			
			Role role = roleRepository.findByRole(Constants.EMPLOYEE_ROLE);
			Employee employee = departmentRemove.getHeadDepartment();
			employee.getUser().setRole(role);
			employee.setDepartment(null);
			
			employeeRepository.save(employee);
			
			departmentRemove.setHeadDepartment(null);
			departmentRepository.delete(departmentOptional.get());
			
			singleResponse.setCode(Constants.SUCCESS_CODE);
			singleResponse.setMessage("Delete success!");
			singleResponse.setSuccess(true);
		}
		else {
			singleResponse.setMessage("Department not found");
		}
		
		return singleResponse;
	}

	@Override
	public SingleResponse<List<DepartmentDto>> getListDepartmentForTeam() {
		SingleResponse<List<DepartmentDto>> singleResponse = new SingleResponse<List<DepartmentDto>>();
		singleResponse.setCode(Constants.SUCCESS_CODE);
		singleResponse.setSuccess(true);
		singleResponse.setData(departmentRepository.findDepartmentForTeam());
		return singleResponse;
	}

	@Override
	public SingleResponse<List<DepartmentDto>> getListDepartment() {
		SingleResponse<List<DepartmentDto>> singleResponse = new SingleResponse<List<DepartmentDto>>();
		singleResponse.setCode(Constants.SUCCESS_CODE);
		singleResponse.setSuccess(true);
		singleResponse.setData(departmentRepository.findAllDepartment());
		return singleResponse;
	}


}
