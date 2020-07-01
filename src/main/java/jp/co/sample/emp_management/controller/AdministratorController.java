package jp.co.sample.emp_management.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.form.InsertAdministratorForm;
import jp.co.sample.emp_management.form.LoginForm;
import jp.co.sample.emp_management.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	//  (SpringSecurityに任せるためコメントアウトしました)
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：管理者を登録する
	/////////////////////////////////////////////////////
	/**
	 * 管理者登録画面を出力します.
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
		session.setAttribute("token", getCsrfToken());
		System.out.println(model.getAttribute("token"));
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param form
	 *            管理者情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model,
			String token) {
		if(!(session.getAttribute("token").equals(token))) {
			System.out.println("トークンが違うよ");
			return "administrator/login";
		}
		session.setAttribute("token", "未使用");
		//バリデーションがエラーを拾ったときページに再遷移
		if(result.hasErrors()) {
			model.addAttribute("inputForm", form);
			return "administrator/insert";
		}
		Administrator administrator = new Administrator();
		// フォームからドメインにプロパティ値をコピー
		BeanUtils.copyProperties(form, administrator);
		try {
			administratorService.insert(administrator);
		}catch(Exception e) {
			model.addAttribute("judge", 1);
			System.out.println("既に登録されています");
			return "administrator/insert";
		}
		return "administrator/login";
	}

	/////////////////////////////////////////////////////
	// ユースケース：ログインをする
	/////////////////////////////////////////////////////
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form
	 *            管理者情報用フォーム
	 * @param result1
	 *            エラー情報格納用オブッジェクト
	 * @return ログイン後の従業員一覧画面
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("inputForm", form);
			return toLogin();
		}
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}
		model.addAttribute("administratorName", administrator.getName());
		
		return "forward:/employee/showList";
	}
	
	/////////////////////////////////////////////////////
	// ユースケース：ログアウトをする
	/////////////////////////////////////////////////////
	/**
	 * ログアウトをします. (SpringSecurityに任せるためコメントアウトしました)
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	/**
	 * トークンの生成.
	 * @return toStringでトークンを返す
	 */
	public static String getCsrfToken() {
	    byte token[] = new byte[16];
	    StringBuffer buf = new StringBuffer();
	    SecureRandom random = null;
	 
	    try {
	      random = SecureRandom.getInstance("SHA1PRNG");
	      random.nextBytes(token);
	 
	      for (int i = 0; i < token.length; i++) {
	        buf.append(String.format("%02x", token[i]));
	      }
	 
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	 
	    return buf.toString();
	  }
	
}
