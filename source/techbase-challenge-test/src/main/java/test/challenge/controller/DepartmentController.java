package test.challenge.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import test.challenge.config.Constants;
import test.challenge.dto.DepartmentDto;
import test.challenge.request.DepartmentRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;
import test.challenge.service.DepartmentService;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;

	//Start GET Method
	@RequestMapping(value = "/list", method =  RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("views/department/list");
		return modelAndView;
	}
	
	@GetMapping("/get-list-department-paging")
	@ResponseBody
	public ResponseEntity<String> getListDepartment(int page, int record, String search) {

		PagingResponse<List<DepartmentDto>> response = departmentService.getAllDepartment(page, record, search);

		HashMap<String, PagingResponse<List<DepartmentDto>>> map = new HashMap<String, PagingResponse<List<DepartmentDto>>>();
		map.put("data", response);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(map);

		return new ResponseEntity<>(json, HttpStatus.OK);
    }

	
	@GetMapping("/detail")
	@ResponseBody
	public SingleResponse<String> detailDepartment(String id) {
    	SingleResponse<String> singleResponse = new SingleResponse<String>();
    	try {
    		singleResponse = departmentService.removeDepartment(id);
		} catch (Exception e) {
			e.printStackTrace();
			singleResponse.setSuccess(false);
			singleResponse.setCode(Constants.ERROR_CODE);
			singleResponse.setMessage("Delete fail!");
		}
		
		return singleResponse;
    }
	//End GET Method
	
	
	//Start POST Method
	@PostMapping("/create")
	@ResponseBody
	public SingleResponse<String> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
    	SingleResponse<String> singleResponse = new SingleResponse<String>();
    	try {
    		singleResponse = departmentService.createDepartment(departmentRequest);
		} catch (Exception e) {
			e.printStackTrace();
			singleResponse.setSuccess(false);
			singleResponse.setCode(Constants.ERROR_CODE);
			singleResponse.setMessage("Create fail!");
		}
		
		return singleResponse;
    }
	//End POST Method
	
	//Start DELETE Method
	@DeleteMapping("/remove")
	@ResponseBody
	public SingleResponse<String> removeDepartment(String id) {
    	SingleResponse<String> singleResponse = new SingleResponse<String>();
    	try {
    		singleResponse = departmentService.removeDepartment(id);
		} catch (Exception e) {
			e.printStackTrace();
			singleResponse.setSuccess(false);
			singleResponse.setCode(Constants.ERROR_CODE);
			singleResponse.setMessage("Delete fail!");
		}
		
		return singleResponse;
    }
	//End DELETE Method

}
