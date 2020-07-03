package jp.co.sample.emp_management.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	// (SpringSecurityに任せるためコメントアウトしました)
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
		// ワンタイムトークンの生成
		session.setAttribute("token", getCsrfToken());

		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param form 管理者情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form, BindingResult result,
			RedirectAttributes redirectAttributes, Model model, String token) {
		// 登録後の更新における二重登録阻止
		if (!(session.getAttribute("token").equals(token))) {
			System.out.println("トークンが違うよ");
			return "administrator/login";
		}
		// 確認用メールアドレスのチェック
		if (!(form.getPassword().equals(form.getConfirmPassword()))) {
			FieldError fieldError = new FieldError(result.getObjectName(), "confirmPassword", "確認用メールアドレスが一致していません");
			result.addError(fieldError);
		}
		// メールアドレスの重複チェック
		Administrator administratorCheck = administratorService.findByMailAddress(form.getMailAddress());
		if (!(administratorCheck == null)) {
			FieldError fieldError = new FieldError(result.getObjectName(), "already", "既に登録されているメールアドレスです");
			result.addError(fieldError);
		}
		// バリデーションがエラーを拾ったときページに再遷移
		if (result.hasErrors()) {
			return "administrator/insert";
		}
		// フォームからドメインにHash化したプロパティ値をコピー
		administratorService.passwordHash(form);
		// トークンの破棄
		session.setAttribute("token", "ここで破棄");

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
	 * @param form    管理者情報用フォーム
	 * @param result1 エラー情報格納用オブッジェクト
	 * @return ログイン後の従業員一覧画面
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginForm form, BindingResult result, RedirectAttributes redirectAttributes,
			Model model) {
		if (result.hasErrors()) {
			return toLogin();
		}
		Administrator administrator = administratorService.findByMailAddress(form.getMailAddress());
		if (administrator == null) {
			FieldError fieldError = new FieldError(result.getObjectName(), "errorMessage", "メールアドレスまたはパスワードが不正です");
			result.addError(fieldError);
			return "administrator/login";
		}
		if (passwordEncoder.matches(form.getPassword(), administrator.getPassword())) {
			session.setAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}
		
		return toLogin();
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
	 * 
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
