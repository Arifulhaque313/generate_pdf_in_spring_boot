package com.practice.reqeust_data_validation.services;

import com.practice.reqeust_data_validation.dtos.PostReqeust;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PostRQService {
    public PostReqeust processPostRequest(@Valid PostReqeust postRequest) {
        return postRequest;
    }
}




