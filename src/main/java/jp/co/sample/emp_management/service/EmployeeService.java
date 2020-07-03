package jp.co.sample.emp_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return　従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}
	
	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee　更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	/**
	 * 曖昧検索.
	 * @param nameParts 検索する文字
	 * @return 従業員一覧ページへ表示
	 */
	public List<Employee> findByName(String nameParts) {
		return employeeRepository.findByName(nameParts);
	}
	/**
	 * ページング処理.
	 * @param EmployeeList DBから検索された従業員一覧情報
	 * @param change 次のページの遷移先
	 * @param model ページの最小/最大、表示
	 */
	public void pageing(List<Employee> EmployeeList, String change, Model model) {
		int page = 1;
		int minPage = 1;
		int maxPage = 1;
		List<Employee> employeeListPart = new ArrayList<Employee>();
		List<Integer> pageCount = new ArrayList<>();
		
		if(change!=null) {
			page = Integer.parseInt(change);
		}
		if(EmployeeList.size()%10==0) {
			maxPage = EmployeeList.size()/10;
		}else {
			maxPage = (int)(EmployeeList.size()/10)+1;
		}
		
		for(int i=(page*10)-9;(i<=page*10)&&(i<=EmployeeList.size());i++) {
			employeeListPart.add(EmployeeList.get(i-1));
		}
		for(int i=1;i<=maxPage;i++) {
			pageCount.add(i);
		}
		
		model.addAttribute("pageNum", page);
		model.addAttribute("minPage", minPage);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("pageCount", pageCount);
		
		model.addAttribute("employeeList", employeeListPart);
	}
}
