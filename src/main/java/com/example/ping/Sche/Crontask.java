package com.example.ping.Sche;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.ping.Model.Userinput;
import com.example.ping.Service.AWXService;
import com.example.ping.Service.UserService;

@Component
public class Crontask {

	private AWXService awxservice;
	private UserService userservice;
	
	
	public Crontask(AWXService awxservice, UserService userservice) {
		this.awxservice = awxservice;
		this.userservice = userservice;
	}
	
	@Scheduled(cron = "0 */5 * * * *")
	public void Jobtriggre() {
		Userinput input  = userservice.getUserinput();
		int getTemplateId = input.getJobTemplateId();
		awxservice.triggerJob(getTemplateId);
	}
}
