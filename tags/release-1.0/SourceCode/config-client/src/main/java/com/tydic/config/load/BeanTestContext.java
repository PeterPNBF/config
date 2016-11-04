package com.tydic.config.load;

public class BeanTestContext {
	
	private String user;
	private String pass;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public void print(){
		System.out.println("user:" + user + " pass:" + pass);
	}

}
