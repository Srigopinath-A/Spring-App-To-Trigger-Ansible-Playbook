package com.example.ping.Service;

import org.springframework.stereotype.Service;
import com.example.ping.Model.Userinput;

@Service
public class UserService {
	
	private Userinput userinput = new Userinput();
	
	public Userinput getUserinput() {
		return userinput;
	}
	
	public void setUserinput(Userinput userinput) {
		this.userinput = userinput;
	}
}