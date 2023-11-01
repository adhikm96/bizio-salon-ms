package com.thebizio.biziosalonms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Service
public class MvcReqHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public MockHttpServletRequestBuilder setUp(MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder setUp(MockHttpServletRequestBuilder builder, Object body)
            throws JsonProcessingException {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));
    }

    public MockHttpServletRequestBuilder setUpWithoutToken(MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder setUpWithoutToken(MockHttpServletRequestBuilder builder, Object body)
            throws JsonProcessingException {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));
    }

    public MockHttpServletRequestBuilder setUpWithoutToken(MockHttpServletRequestBuilder builder, Object body, String sigHeader)
            throws JsonProcessingException {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .header("stripe-signature",sigHeader);
    }
}
