package com.prashanth.resume_classifier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MlService {

    private final RestTemplate restTemplate;
    private static final String ML_API_URL = "https://resume-ml-service-9f0z.onrender.com/predict";

    private static final int MAX_RETRIES = 3;


    public String getPrediction(String text){

        int attempt = 0;
        log.info("Entered into getPrediction");

        while(attempt < MAX_RETRIES){
            try{
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<Map<String,String>> request = new HttpEntity<>(Map.of("text",text),headers);

                ResponseEntity<Map> response = restTemplate.exchange(
                        ML_API_URL,
                        HttpMethod.POST,
                        request,
                        Map.class
                );

                return Objects.requireNonNull(response.getBody()).get("category").toString();


            }catch (Exception e){
                attempt++;
                log.warn("ML API call failed. Attempt: {}", attempt, e);

                if (attempt >= MAX_RETRIES) {
                    log.error("ML API failed after retries", e);
                    throw new RuntimeException("ML service unavailable");
                }

                // ⏱️ Wait before retry (2 sec)
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }

        return "Unknown";


    }
}
