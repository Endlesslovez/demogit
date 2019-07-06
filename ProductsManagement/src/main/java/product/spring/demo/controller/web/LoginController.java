package product.spring.demo.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import product.spring.demo.entities.Users;
import product.spring.demo.services.UserServive;

@Controller
public class LoginController {
//@Autowired
//private UserServive userService;
//	@RequestMapping(value =  "/login" , method = RequestMethod.GET)
//	public ModelAndView login() {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("login");
//		return modelAndView;
//	}
//	
//	@GetMapping("/logout")
//	public String logout(HttpServletRequest request,HttpServletResponse response) {
//		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
//		if(auth!=null) {
//			new SecurityContextLogoutHandler().logout(request, response, auth);
//		}
//		return "redirect:/login";
//	}
//	
//	@RequestMapping(value = "/registration", method = RequestMethod.GET)
//	public ModelAndView registration() {
//		ModelAndView modelAndView = new ModelAndView();
//		Users user = new Users();
//		modelAndView.addObject("user", user);
//		modelAndView.setViewName("registration");
//		return modelAndView;
//	}
//
//	@RequestMapping(value = "/registration", method = RequestMethod.POST)
//	public ModelAndView createNewUser(@Valid Users user, BindingResult bindingResult) {
//		ModelAndView modelAndView = new ModelAndView();
//		Users userExists = userService.findUserByEmail(user.getEmail());
//		if (userExists != null) {
//			bindingResult.rejectValue("email", "error.user",
//					"There is already a user registered with the email provided");
//		}
//		if (bindingResult.hasErrors()) {
//			modelAndView.setViewName("registration");
//		} else {
//			userService.saveUser(user);
//			modelAndView.addObject("successMessage", "User has been registered successfully");
//			modelAndView.addObject("user", new Users());
//			modelAndView.setViewName("registration");
//
//		}
//		return modelAndView;
//	}
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String loginPage(@RequestParam(value = "error", required = false) String error,
	                            @RequestParam(value = "logout", required = false) String logout,
	                            Model model) {
	        String errorMessge = null;
	        if(error != null) {
	            errorMessge = "Username or Password is incorrect !!";
	        }
	        if(logout != null) {
	            errorMessge = "You have been successfully logged out !!";
	        }
	        model.addAttribute("errorMessge", errorMessge);
	        return "login";
	    }
	  
	    @RequestMapping(value="/logout", method = RequestMethod.GET)
	    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null){   
	            new SecurityContextLogoutHandler().logout(request, response, auth);
	        }
	        return "redirect:/login?logout=true";
	    }
}
