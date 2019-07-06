package product.spring.demo.controller.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import product.spring.demo.services.CategoryService;
import product.spring.demo.vo.CategoryVO;

@Controller
@RequestMapping("/admincategoy")
public class AdminCategoryAController {

	@Autowired
	private CategoryService ca;

	@RequestMapping(value = "categorylist", method = RequestMethod.GET)
	public ModelAndView categorylist() {
		ModelAndView modelAndView = new ModelAndView("categorylist");
		modelAndView.addObject("categoryList", ca.getAllCategory());
		return modelAndView;

	}

	@RequestMapping("/categorylist/deletecategory/{id}")
	public RedirectView deleteCategory(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
		boolean result = ca.deleteCategory(id);
		String message = result == true ? "Success!" : "False";
		redirectAttributes.addFlashAttribute("msg", message);
		return new RedirectView("/admincategoy/categorylist", true);
	}

	@GetMapping(value = "/categorylist/editcategory/{id}")
	public ModelAndView editCategory(@PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView("editcategory");
		modelAndView.addObject("categoryVo", ca.getIdCategory1(id));
		return modelAndView;
	}

	@PostMapping(value = "/admincategoy/editcategory")
	public ModelAndView editCategory1(@ModelAttribute("categoryVo") @Valid CategoryVO vo, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, ModelMap model) {
		boolean success = !bindingResult.hasErrors();
		if (success) {

			ca.findCategoryById(vo);
//			redirectAttributes.addFlashAttribute("msg", "Success!");
			model.addAttribute("msg", "Đã sửa thành công ");
			return new ModelAndView("redirect:/categorylist");
		}
		model.addAttribute("msg", "Sửa thất bại");
		return new ModelAndView("editcategory");
	}

	@RequestMapping("/createcategory")
	public ModelAndView createCategory() {
		ModelAndView modelAndView = new ModelAndView("createcategory");
//		modelAndView.addObject("categoryList", ca.getAllCategory());
		modelAndView.addObject("categoryVo", new CategoryVO());
		return modelAndView;
	}

	@PostMapping(value = "/createcategory")
	public ModelAndView createCategory1( @ModelAttribute("categoryVo") @Valid CategoryVO vo, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, ModelMap model) {
		boolean success = !bindingResult.hasErrors();
		if (success) {
			ca.createCategory(vo);
//			redirectAttributes.addFlashAttribute("msg", "Success!");
			model.addAttribute("msg", "Đã tạo mới thành công một  nhóm hàng");
			return new ModelAndView("redirect:/admincategoy/categorylist");
		}
		model.addAttribute("msg", "Chưa tạo được nhóm hàng");
		return new ModelAndView("createcategory");
	}

}
