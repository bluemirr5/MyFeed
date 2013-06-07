package com.nnggstory.feedfactory.model;

/**
 * 사용자 뷰 모델 클래스.
 * 
 * @author bluemirr5
 *
 */
public class ViewUserModel {
	private String id = null;
	private String password = null;
	private String email = null;
	private String nickName = null;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void matchToViewModel(DataUserModel dataModel) {
		if(dataModel != null) {
			id = dataModel.id;
			email = dataModel.email;
			password = dataModel.password;
			nickName = dataModel.nickName;
		}
	}
}
