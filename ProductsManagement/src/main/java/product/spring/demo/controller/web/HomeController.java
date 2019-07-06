package product.spring.demo.controller.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.models.Model;
import product.spring.demo.entities.category;
import product.spring.demo.entities.product;
import product.spring.demo.entities.Users;
import product.spring.demo.services.CategoryService;
import product.spring.demo.services.ProductService;
import product.spring.demo.services.UserServive;
import product.spring.demo.vo.CategoryVO;
import product.spring.demo.vo.ProductVO;
import product.spring.demo.vo.UserVO;

@Controller

public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService ca;

	@RequestMapping("/")
	public String index() {
		return "homePage";
	}

	@RequestMapping(value = "/productlistoppo", method = RequestMethod.GET)
	public ModelAndView productPage1() {
		ModelAndView modelAndView = new ModelAndView("productlistoppo");
		modelAndView.addObject("productList1", productService.getAllProduct());
		return modelAndView;
	}

	@RequestMapping(value = "/productlistoppo/productdetail/{id}")
	public ModelAndView productDetail(@PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView("productdetail");
		modelAndView.addObject("productModelAttribute", productService.getProductId(id));
			return modelAndView;
	}

}
