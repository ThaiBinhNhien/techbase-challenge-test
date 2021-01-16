package test.challenge.service;

import java.util.List;

import test.challenge.dto.DepartmentDto;
import test.challenge.request.DepartmentRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;


public interface DepartmentService {
	
	PagingResponse<List<DepartmentDto>> getAllDepartment(int page, int record, String search);
	
	SingleResponse<List<DepartmentDto>> getListDepartment();
	
	SingleResponse<List<DepartmentDto>> getListDepartmentForTeam();
	
	SingleResponse<DepartmentDto> getDetailDepartment(String id);
	
	SingleResponse<String> createDepartment(DepartmentRequest departmentRequest);
	
	SingleResponse<String> removeDepartment(String id);

}
