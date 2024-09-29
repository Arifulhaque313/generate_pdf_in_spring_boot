package com.practice.reqeust_data_validation.services;

import com.practice.reqeust_data_validation.dtos.PostReqeust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PdfGeneratorService {

    private final TemplateEngine templateEngine;
    @Autowired
    public PdfGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(PostReqeust documentRequest) throws Exception {
        // Create a Thymeleaf context and add the document data
        Context context = new Context();
        context.setVariable("document", documentRequest);

        // Add the base64 encoded logo to the context
        String base64Logo = getBase64Image("static/images/logo.png");
        context.setVariable("base64Image", base64Logo);

        // Log the base64Logo to debug

        // Render the HTML content using Thymeleaf template
        String htmlContent = templateEngine.process("pdf_template", context);

        // Convert HTML to PDF using iTextRenderer
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        System.out.println("HTML Content: " + htmlContent);

        // Output the PDF to a byte array
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }

    private String getBase64Image(String path) {
        try {
            ClassPathResource imgFile = new ClassPathResource(path);
            byte[] bytes = imgFile.getInputStream().readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
