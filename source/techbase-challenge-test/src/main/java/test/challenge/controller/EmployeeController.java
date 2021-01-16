package test.challenge.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import test.challenge.config.Constants;
import test.challenge.dto.EmployeeDto;
import test.challenge.dto.TeamDto;
import test.challenge.entity.User;
import test.challenge.request.UserRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;
import test.challenge.service.EmployeeService;
import test.challenge.service.TeamService;
import test.challenge.service.UserService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TeamService teamService;

	//Start Get Method
	@RequestMapping(value = "/list", method =  RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getEmployee().getFullName() + " ");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("views/employee/list");
		return modelAndView;
	}
	
	@GetMapping("/get-list-employee-paging")
	@ResponseBody
	public ResponseEntity<String> getListAllEmployee(int page, int record, String search) {
		PagingResponse<List<EmployeeDto>> response = employeeService.getAllEmployee(page, record, search);
		
		HashMap<String, PagingResponse<List<EmployeeDto>>> map = new HashMap<String, PagingResponse<List<EmployeeDto>>>();
		map.put("data", response);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(map);

		return new ResponseEntity<>(json, HttpStatus.OK);
		
    }
	
	@GetMapping("/get-list-employee-for-department")
	@ResponseBody
	public SingleResponse<List<EmployeeDto>> getListEmployeeForDepartment() {
		SingleResponse<List<EmployeeDto>> response = employeeService.getEmployeeDepartment();
		return response;
    }
	
	@GetMapping("/get-list-employee-for-team")
	@ResponseBody
	public SingleResponse<List<EmployeeDto>> getListEmployeeForTeam() {
		SingleResponse<List<EmployeeDto>> response = employeeService.getEmployeeForTeam();
		return response;
    }
	
	@GetMapping("/get-list-team-for-employee")
	@ResponseBody
	public SingleResponse<List<TeamDto>> getListTeamForEmployee() {
		SingleResponse<List<TeamDto>> response = teamService.getAllTeam();
		return response;
    }
	//End Get Method
	
	
	//Start Post Method
	@PostMapping("/create")
	@ResponseBody
	public SingleResponse<String> createEmployee(@RequestBody UserRequest userRequest) {
    	SingleResponse<String> singleResponse = new SingleResponse<String>();
    	try {
    		singleResponse = employeeService.createEmployee(userRequest);
		} catch (Exception e) {
			e.printStackTrace();
			singleResponse.setSuccess(false);
			singleResponse.setCode(Constants.ERROR_CODE);
			singleResponse.setMessage("Create fail!");
		}
		return singleResponse;
    }
	//End Post Method
	
	//Start delete method
	@DeleteMapping("/remove")
	@ResponseBody
	public SingleResponse<String> removeDepartment(String id) {
    	SingleResponse<String> singleResponse = new SingleResponse<String>();
    	try {
    		singleResponse = employeeService.removeEmployee(id);
		} catch (Exception e) {
			e.printStackTrace();
			singleResponse.setSuccess(false);
			singleResponse.setCode(Constants.ERROR_CODE);
			singleResponse.setMessage("Delete fail!");
		}
		
		return singleResponse;
    }
	//End delete method

}
