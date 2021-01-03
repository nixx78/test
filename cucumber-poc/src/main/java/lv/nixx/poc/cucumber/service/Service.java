package lv.nixx.poc.cucumber.service;

import java.util.Collection;

public class Service {
	
	public void login(String user, String password) {
		if ( !user.equalsIgnoreCase("user1") && password.equalsIgnoreCase("pass1") ) {
			throw new IllegalStateException("user or password not valid");
		}
	}
	
	public void logout(){
	}
	
	public int calculate(int a, int b) {
		return a + b;
	}
	
	public Collection<String> toUppercase(Collection<String> source) {
		return source;
	}

}
