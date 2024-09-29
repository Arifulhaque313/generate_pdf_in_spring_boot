package com.practice.reqeust_data_validation.controllers;

import com.practice.reqeust_data_validation.dtos.ApiResponse;
import com.practice.reqeust_data_validation.dtos.PostReqeust;
import com.practice.reqeust_data_validation.services.PdfGeneratorService;
import com.practice.reqeust_data_validation.services.PostRQService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostRequestController {

    private final PostRQService postRQService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    // Constructor-based Dependency Injection
    @Autowired
    public PostRequestController(PostRQService postRQService) {
        this.postRQService = postRQService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostReqeust>> createDocument(@Valid @RequestBody PostReqeust postReqeust) {
        // Use the service to process the data
        PostReqeust processedData = postRQService.processPostRequest(postReqeust);

        // Create the ApiResponse object with the processed data
        ApiResponse<PostReqeust> response = new ApiResponse<>(200, "Validation successful", processedData);

        // Return the response entity
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@Valid @RequestBody PostReqeust documentRequest) {
        try {
            // Generate the PDF using the service
            byte[] pdfContent = pdfGeneratorService.generatePdf(documentRequest);

            // Set headers for the PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf");
            headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

            // Return the PDF as a byte array
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}