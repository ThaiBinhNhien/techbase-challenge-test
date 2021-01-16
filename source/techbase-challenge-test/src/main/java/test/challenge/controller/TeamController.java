package test.challenge.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import test.challenge.dto.TeamDto;
import test.challenge.dto.TeamEmployeeDto;
import test.challenge.entity.User;
import test.challenge.request.TeamRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;
import test.challenge.service.DepartmentService;
import test.challenge.service.TeamService;
import test.challenge.service.UserService;

@Controller
@RequestMapping(value = "/team")
public class TeamController {

	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private TeamService teamService;

	//Start GET Method
	@RequestMapping(value = "/list", method =  RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getEmployee().getFullName() + " ");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("views/team/list");
		return modelAndView;
	}

	@GetMapping("/get-list-team-paging")
	@ResponseBody
	public ResponseEntity<String> getListTeamPaging(int page, int record, String search) {
		PagingResponse<List<TeamDto>> response = teamService.getAllTeam(page, record, search);

		HashMap<String, PagingResponse<List<TeamDto>>> map = new HashMap<String, PagingResponse<List<TeamDto>>>();
		map.put("data", response);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(map);

		return new ResponseEntity<>(json, HttpStatus.OK);

	}
	
	@GetMapping("/get-list-department-for-team")
	@ResponseBody
	public SingleResponse<List<DepartmentDto>> getListDepartmentForTeam() {
		SingleResponse<List<DepartmentDto>> response = departmentService.getListDepartmentForTeam();
		return response;
    }
	
	@GetMapping(value="/get-list-team-of-employee")
	@ResponseBody
	public SingleResponse<List<TeamEmployeeDto>> getTeamsEmployeeJoined(String id){
		SingleResponse<List<TeamEmployeeDto>> response = teamService.getListTeamByEmployee(id);
		return response;
	}
	//End GET Method

	//Start Post Method
	@PostMapping("/create")
	@ResponseBody
	public SingleResponse<String> createTeam(@Valid @RequestBody TeamRequest teamRequest) {
		SingleResponse<String> singleResponse = new SingleResponse<String>();
		try {
			singleResponse = teamService.createTeam(teamRequest);
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
	public SingleResponse<String> removeTeam(String id) {
		SingleResponse<String> singleResponse = new SingleResponse<String>();
		try {
			singleResponse = teamService.removeTeam(id);
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
