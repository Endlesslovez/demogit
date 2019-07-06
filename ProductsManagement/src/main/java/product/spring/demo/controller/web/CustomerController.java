package product.spring.demo.controller.web;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import product.spring.demo.vo.CustomerVO;


@Controller
@RequestMapping("/customer")
public class CustomerController {

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor= new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	@RequestMapping("/showForm")
	public String showForm(ModelMap model) {
	model.addAttribute("customer", new CustomerVO());
	return "customer";
	}
	@RequestMapping("processForm")
	public String processForm(@Validated @ModelAttribute("customer") CustomerVO customerVO,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "customer";
		}else {
			return "comfirmation";
		}
	}
}
