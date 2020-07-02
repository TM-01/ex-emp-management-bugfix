package jp.co.sample.emp_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator　管理者情報
	 */
	public void insert(Administrator administrator) {
		administratorRepository.insert(administrator);
	}
	
	/**
	 * ログインをします.
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 管理者情報　存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String passward) {
		Administrator administrator = administratorRepository.findByMailAddressAndPassward(mailAddress, passward);
		return administrator;
	}
	
	public Administrator load(int administratorId) {
		Administrator administrator = administratorRepository.load(administratorId);
		return administrator;
	}
	
	/**
	 * メールアドレスから管理者情報を取得します.
	 * 
	 * @param mailAddress メールアドレス
	 * @return 管理者情報 存在しない場合はnullを返します
	 */
	public Administrator findByMailAddress(String mailAddress) {
		return administratorRepository.findByMailAddress(mailAddress);
	}
	
	/**
	 * メールアドレスが一致するものをすべて検索.
	 * @param mailAddress
	 * @return リストですべて返す
	 */
	public List<Administrator> findAll(String mailAddress) {
		return administratorRepository.findAll(mailAddress);
	}
}
