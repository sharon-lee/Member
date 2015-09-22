package com.webapp.command;

public class LoginCommand {
	String email;
	String password;
	boolean rememberID;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRememberID() {
		return rememberID;
	}
	public void setRememberID(boolean rememberID) {
		this.rememberID = rememberID;
	}
	
	@Override
	public String toString() {
		
		return "email = " + email + " pwd = " + password + " remember = " + rememberID;
	}
	
}
