package jp.co.sample.emp_management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.service.EmployeeService;
import net.arnx.jsonic.JSON;

/** 商品検索フォームのオートコンプリート用コントローラ. */
@Controller
@RequestMapping("/getAutoComplete")
public class GetAutoCompleteController {
	@Autowired
	private EmployeeService employeeService;

    /** 全商品名をオートコンプリートに渡す. */
    @ResponseBody
    @RequestMapping
    public String getAutoComplete(){
    	List<String> nameList = new ArrayList<>();
    	for(Employee employee : employeeService.showList()) {
    		nameList.add(employee.getName());
    	}
        
        return JSON.encode(nameList);
    }
}