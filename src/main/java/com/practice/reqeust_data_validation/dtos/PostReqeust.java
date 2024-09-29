package com.practice.reqeust_data_validation.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostReqeust {

    @NotNull(message = "Document ID is required")
    private Integer document_id;

    @NotBlank(message = "Language is required")
    @Pattern(regexp = "^[a-z]{2}$", message = "Language should be a valid ISO 639-1 code")
    private String language;

    @NotBlank(message = "Type is required")
    private String type;
}