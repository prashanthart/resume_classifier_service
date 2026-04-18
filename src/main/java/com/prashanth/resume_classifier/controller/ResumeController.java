package com.prashanth.resume_classifier.controller;

import com.prashanth.resume_classifier.dto.PredictionResponse;
import com.prashanth.resume_classifier.service.MlService;
import com.prashanth.resume_classifier.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/resume")
@RequiredArgsConstructor
@RestController
public class ResumeController {

    private final MlService mlService;
    private final PdfService pdfService;

    @GetMapping("/get")
    private String get(){
        return "ok";
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<PredictionResponse> uploadResume(@RequestParam("file") MultipartFile file){


        try{

            if(file.isEmpty()){
                return ResponseEntity.badRequest()
                        .body(PredictionResponse.error("File is empty."));
            }

            String text = pdfService.extractText(file);
            String category = mlService.getPrediction(text);

            return  ResponseEntity.ok(PredictionResponse.success(category));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(PredictionResponse.error(e.getMessage()));
        }

    }
}
