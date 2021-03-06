package com.internousdev.magenda.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.magenda.dao.UserInfoDAO;
import com.internousdev.magenda.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordConfirmAction extends ActionSupport implements SessionAware{

	private String loginId;
	private String password;
	private String newPassword;
	private String reConfirmationPassword;
	private Map<String, Object> session;

	public String execute() throws SQLException{

		if(!session.containsKey("mCategoryDTOList")){
			return "timeout";
		}

		List<String> loginIdErrorMessageList = new ArrayList<String>();
		List<String> passwordErrorMessageList = new ArrayList<String>();
		List<String> passwordIncorrectErrorMessageList = new ArrayList<String>();
		List<String> newPasswordErrorMessageList = new ArrayList<String>();
		List<String> reConfirmationNewPasswordErrorMessageList = new ArrayList<String>();
		List<String> newPasswordIncorrectErrorMessageList = new ArrayList<String>();
		String result = ERROR;

		session.put("resetLoginId", loginId);
		session.remove("loginIdErrorMessageList");
		session.remove("passwordErrorMessageList");
		session.remove("passwordIncorrectErrorMessageList");
		session.remove("newPasswordErrorMessageList");
		session.remove("reConfirmationNewPasswordErrorMessageList");
		session.remove("newPasswordIncorrectErrorMessageList");

		InputChecker inputChecker = new InputChecker();

		loginIdErrorMessageList = inputChecker.doCheck("ユーザーID", loginId, 1, 8, true, false, false, true, false, false, false, false, false);
		passwordErrorMessageList = inputChecker.doCheck("現在のパスワード", password, 1, 16, true, false, false, true, false, false, false, false, false);
		newPasswordErrorMessageList = inputChecker.doCheck("新しいパスワード", newPassword, 1, 16, true, false, false, true, false, false, false, false, false);
		reConfirmationNewPasswordErrorMessageList = inputChecker.doCheck("新しいパスワード(再確認）", reConfirmationPassword, 1, 16, true, false, false, true, false, false, false, false, false);

		if(loginIdErrorMessageList.size() > 0
		|| passwordErrorMessageList.size() > 0
		|| newPasswordErrorMessageList.size() > 0
		|| reConfirmationNewPasswordErrorMessageList.size() > 0) {

			session.put("loginIdErrorMessageList", loginIdErrorMessageList);
			session.put("passwordErrorMessageList", passwordErrorMessageList);
			session.put("newPasswordErrorMessageList", newPasswordErrorMessageList);
			session.put("reConfirmationNewPasswordErrorMessageList", reConfirmationNewPasswordErrorMessageList);
			result= ERROR;

		} else {

			UserInfoDAO userInfoDAO = new UserInfoDAO();
			if(userInfoDAO.isExistsUserInfo(loginId, password)) {
				newPasswordIncorrectErrorMessageList = inputChecker.doPasswordCheck(newPassword, reConfirmationPassword);
				if(newPasswordIncorrectErrorMessageList.size()==0){
					int beginIndex = 0;
					int endIndex = 1;
					StringBuilder stringBuilder = new StringBuilder("****************");
				String concealPassword = stringBuilder.replace(beginIndex, endIndex, newPassword.substring(beginIndex,endIndex)).toString();
				  session.put("loginId", loginId);
				  session.put("newPassword", newPassword);
				  session.put("concealedPassword", concealPassword);
				  result = SUCCESS;
				}else{
				  session.put("newPasswordIncorrectErrorMessageList", newPasswordIncorrectErrorMessageList);
				  result = ERROR;
				}
			} else {
				passwordIncorrectErrorMessageList.add("ユーザーIDまたは現在のパスワードが異なります。");
				session.put("passwordIncorrectErrorMessageList", passwordIncorrectErrorMessageList);
				result = ERROR;
			}

		}
		return result;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReConfirmationPassword() {
		return reConfirmationPassword;
	}

	public void setReConfirmationPassword(String reConfirmationPassword) {
		this.reConfirmationPassword = reConfirmationPassword;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
