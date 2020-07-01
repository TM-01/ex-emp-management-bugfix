package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message="Eメールの形式が不正です")
	private String mailAddress;
	/** パスワード */
	@Size(min=4, max=12, message="パスワードは4文字以上12文字以下でで入力してください")
	private String password;
	/** 確認用パスワード */
	private String confirmPassword;
	/** 既に登録済み*/
	private String already;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getAlready() {
		return already;
	}
	public void setAlready(String already) {
		this.already = already;
	}
	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", already=" + already + ", getName()=" + getName()
				+ ", getMailAddress()=" + getMailAddress() + ", getPassword()=" + getPassword()
				+ ", getConfirmPassword()=" + getConfirmPassword() + ", getAlready()=" + getAlready() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
