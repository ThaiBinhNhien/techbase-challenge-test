package test.challenge.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import test.challenge.config.Constants;
import test.challenge.entity.Role;
import test.challenge.entity.User;
import test.challenge.service.EmployeeService;
import test.challenge.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value= {"/","/login"}, method = RequestMethod.GET)
	public ModelAndView login() throws Exception{
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("views/login");
		return modelAndView;
	}

	@RequestMapping(value="/logined", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/login");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && !auth.getName().isEmpty()) {
			User loginedUser = userService.findUserByUserName(auth.getName());
			if (loginedUser != null) {

				request.getSession().setAttribute("user_fullname", loginedUser.getEmployee().getFullName());
				request.getSession().setAttribute("user_id", loginedUser.getId());

				Role firstRole = loginedUser.getRole();
				if(firstRole != null) {
					switch (firstRole.getRole()) {
					case Constants.DIRECTOR_ROLE:
						//process for director role
						mav.setViewName("redirect:/department/list");
						request.getSession().setAttribute("user_roles", 3);
						break;
					case Constants.MANAGER_ROLE:
						//process for manager role
						mav.setViewName("redirect:/team/list");
						request.getSession().setAttribute("user_roles", 2);
						break;
					case Constants.TEAMLEADER_ROLE:
						//process for teamleader role
						mav.setViewName("redirect:/employee/list");
						request.getSession().setAttribute("user_roles", 1);
						break;
					default:
						mav.setViewName("redirect:/profile");
						request.getSession().setAttribute("user_roles", 0);
						break;
					}
				}
			}
		}
		return mav;
	}

	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		
		modelAndView.addObject("userLogined", user);
		modelAndView.setViewName("views/profile");
		
		return modelAndView;
	}


	@RequestMapping(value="/access-denied", method = RequestMethod.GET)
	public ModelAndView accessDenied(Principal principal){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			User loginedUser = userService.findUserByUserName(auth.getName());

			modelAndView.addObject("userInfo", loginedUser.getEmployee().getFullName());
			modelAndView.addObject("message", "Content Available Only for Users with Admin Role");
		}
		modelAndView.setViewName("views/403");
		return modelAndView;
	}
}
