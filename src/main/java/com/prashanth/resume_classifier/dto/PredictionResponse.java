package com.prashanth.resume_classifier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PredictionResponse {
    private String category;
    private boolean error;
    private String errorMessage;

    public static PredictionResponse success(String category){
        PredictionResponse res = new PredictionResponse();
        res.setCategory(category);
        res.setError(false);
        return res;
    }

    public static PredictionResponse error(String error){
        PredictionResponse res = new PredictionResponse();
        res.setError(true);
        res.setErrorMessage(error);
        return res;
    }
}
