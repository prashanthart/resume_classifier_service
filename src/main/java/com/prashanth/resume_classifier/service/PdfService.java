package com.prashanth.resume_classifier.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class PdfService {

    public String extractText(MultipartFile file){

        try(PDDocument document = Loader.loadPDF(file.getBytes())){

            if(document.getNumberOfPages()==0){
                return "";
            }
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            log.info("Entered into extractText and extracted");

            return text.replaceAll("\\s+"," ").trim();


        } catch (IOException e) {
            log.error("Error extracting text",e);
            throw new RuntimeException("Failed to process PDF");
        }
    }

}
