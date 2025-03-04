package com.example.ping.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AWXService {

    @Value("${awx.url}")
    private String awxUrl;

    @Value("${awx.token}")
    private String awxToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AWXService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public JsonNode triggerJob(int jobTemplateId) {
        String url = String.format("%s/api/v2/job_templates/%d/launch/", awxUrl, jobTemplateId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(awxToken);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

        return response.getBody();
    }

    public JsonNode getJobResult(int jobId) {
        String url = String.format("%s/api/v2/jobs/%d/", awxUrl, jobId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(awxToken);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

        return response.getBody();
    }
    public String getJobOutput(int jobId) {
        String url = String.format("%s/api/v2/jobs/%d/stdout/?format=txt", awxUrl, jobId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setBearerAuth(awxToken);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}