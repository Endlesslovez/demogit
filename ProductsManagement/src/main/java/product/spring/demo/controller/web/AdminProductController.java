package product.spring.demo.controller.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

import product.spring.demo.entities.product;
import product.spring.demo.services.CategoryService;
import product.spring.demo.services.ProductService;
import product.spring.demo.vo.ProductVO;

@Controller
@RequestMapping("/adminproduct")
public class AdminProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	ServletContext servletContext;

	@Autowired
	private CategoryService ca;
	
	@RequestMapping(value = "adminproduct1", method = RequestMethod.GET)
	public ModelAndView productPage(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView("productListPage");
		modelAndView.addObject("productList", productService.getAllProduct());
		return modelAndView;
	}
	
	@RequestMapping(value = "createproduct", method = RequestMethod.GET)
	public ModelAndView createProduct() {
		ModelAndView modelAndView = new ModelAndView("addproduct");
		modelAndView.addObject("categoryList", ca.getAllCategory());
		modelAndView.addObject("productModelAttribute", new ProductVO());
		return modelAndView;
	}
	

	@PostMapping(value = "createproduct")
	public String create(@Validated @ModelAttribute("productModelAttribute") ProductVO productVo, 
			BindingResult bindingResult,ModelMap map) {
		if (bindingResult.hasErrors()) {
			map.addAttribute("msg", "Vui lòng sửa các lỗi sau đây");
			return "addproduct";	
		} else {
			product products = new product();
			BeanUtils.copyProperties(productVo, products);
			MultipartFile file = productVo.getAnh();
			String fileName = file.getOriginalFilename();
			if(fileName.length()==0){
				map.addAttribute("msg", "Sửa thất bại");
				return "addproduct";	
			}
			System.out.println(fileName);
			products.setAnh(fileName);
			try {
				String webappRoot = servletContext.getRealPath("/images/");
				String filename = webappRoot + fileName;
				byte[] bytes = file.getBytes();
				Path path = Paths.get(filename);
				Files.write(path, bytes);
			
				productService.save(products);
				map.addAttribute("productList", productService.getAllProduct());
			} catch (IOException e) {
				e.printStackTrace();

			}
			map.addAttribute("msg", "Chúc mừng bạn đã thêm thành công");
			return "productListPage";
		}
	}


	@RequestMapping("/adminproduct1/deleteProduct/{id}")
	public RedirectView deleteProduct(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
		boolean result = productService.deleteProduct(id);
		String message = result == true ? "Xóa thành công!!!" : "Failed!";
		redirectAttributes.addFlashAttribute("msg", message);
		return new RedirectView("/adminproduct/adminproduct1", true);
	}

	@RequestMapping(value = "/adminproduct1/editproduct/{id}", method = RequestMethod.GET)
	public ModelAndView editProduct(@PathVariable("id") String id) {
		ModelAndView m = new ModelAndView("editproduct");
		m.addObject("categoryList", ca.getAllCategory());
		product products = productService.getProductId(id);
		ProductVO vo = new ProductVO();
		BeanUtils.copyProperties(products, vo);
		m.addObject("productModelAttribute", vo);
		return m;
	}

}
