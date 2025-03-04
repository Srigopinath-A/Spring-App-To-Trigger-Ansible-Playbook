package com.example.ping.Controller;

import com.example.ping.Model.Userinput;
import com.example.ping.Service.AWXService;
import com.example.ping.Service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/awx")
public class AWXController {

    private final AWXService awxService;
    private final UserService userService;

    @Autowired
    public AWXController(AWXService awxService, UserService userService) {
        this.awxService = awxService;
        this.userService = userService;
    }

    @PostMapping("/trigger-job/{jobTemplateId}")
    public JsonNode triggerJob(@PathVariable int jobTemplateId) {
        Userinput userinput = new Userinput();
        userinput.setJobTemplateId(jobTemplateId);
        userService.setUserinput(userinput);
        return awxService.triggerJob(jobTemplateId);
    }

    @GetMapping("/job-result/{jobId}")
    public JsonNode getJobResult(@PathVariable int jobId) {
        return awxService.getJobResult(jobId);
    }

    @GetMapping("/job-output/{jobId}")
    public String getJobOutput(@PathVariable int jobId) {
        return awxService.getJobOutput(jobId);
    }

    @GetMapping("/last-input")
    public String getLastInput() {
        Userinput userinput = userService.getUserinput();
        return "Job Template ID: " + userinput.getJobTemplateId();
    }
}