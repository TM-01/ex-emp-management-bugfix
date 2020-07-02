package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * ログイン時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class LoginForm {
	/** メールアドレス */
	@Email(message="Eメールの形式が不正です")
	private String mailAddress;
	/** パスワード */
	@Size(min=4, max=12, message="パスワードは4文字以上12文字以下でで入力してください")
	private String password;
	private String errorMessage;
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "LoginForm [mailAddress=" + mailAddress + ", password=" + password + ", errorMessage=" + errorMessage
				+ "]";
	}
	
	

	

}
