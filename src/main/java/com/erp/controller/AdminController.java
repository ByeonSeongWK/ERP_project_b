package com.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.service.AdminService;
import com.erp.vo.Department;
import com.erp.vo.Users;

// 관리자 컨트롤러
@Controller
@RequestMapping(value="/admin/*")
public class AdminController {
	
	@Inject
	AdminService service;
	
//	List<Department> dept_list = null;
//	public AdminController() throws Exception {
//		dept_list = service.getDeptList();
//	}
	
	
	// -- admin page
	// adminMain(관리자 메인)
	@RequestMapping(value ="/adminMain", method = RequestMethod.GET)
	public String main(Model model) {
		return "admin/adminMain";
	}
	
	
	// add_employee(사원추가)
	@RequestMapping(value ="/add_employee", method = RequestMethod.GET)
	public String add_employee(Model model) throws Exception {
		
		List<Department> dept_list = service.getDeptList();
		model.addAttribute("dept_list", dept_list);
		
		return "admin/add_employee";
	}

	// search_dept(부서검색)
	@RequestMapping(value ="/search_dept", method = RequestMethod.GET)
	public String search_dept(Model model) throws Exception {
		
		List<Department> dept_list = service.getDeptList();
		model.addAttribute("dept_list", dept_list);
		
		return "admin/search_dept";
	}
	
	// correct_auth(권한부여)
	@RequestMapping(value ="/correct_auth", method = RequestMethod.GET)
	public String correct_auth(Model model) {
		return "admin/correct_auth";
	}
	
	
	// 사원등록할때 사원번호, 부서 정보 불러오기
	@RequestMapping(value = "/getUserNumCnt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getUserNumCnt(String dept_num) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		
		Department department = service.getDepartment(dept_num);
		String user_num = service.makeUser_num(dept_num);
	
		map.put("user_num", user_num);
		map.put("dept_num", department.getDept_num());
		map.put("dept_name", department.getDept_name());
		
		return map;
	}
	
	// search_employee(사원관리)
	@RequestMapping(value ="/search_employee", method = RequestMethod.GET)
	public String search_employee(Model model) throws Exception {
		
		List<Users> user_list = service.getUsersList();
		model.addAttribute("user_list", user_list);
		
		return "admin/search_employee";
	}

	
	// searchName(사원검색)
	@RequestMapping(value ="/searchName", method = RequestMethod.GET)
	@ResponseBody						//뷰에서 받아 오는 값
	// 리스폰스 바디를 추가해주면 결과물을 페이지에서 바로 	보여줌 
	public List<Users> searchName(String user_name) throws Exception {
		
		List<Users> list = service.searchName(user_name);
		
		return list;
	}
	

	// url -- joinAction 일 경우
	@RequestMapping(value = "/joinAction", method = RequestMethod.POST)
	public String joinAction(Users users, String addr1, String addr2, String addr3) throws Exception {
		
		users.setUser_add(addr1 + " " + addr2 + " " + addr3);
		
		service.joinAction(users);
		
		return "redirect:/admin/add_employee";
	}
	
	@RequestMapping(value = "/updateDetpAction", method = RequestMethod.POST)
	public String updateDetpAction(Department department) throws Exception {
		
		return null;
	}
}