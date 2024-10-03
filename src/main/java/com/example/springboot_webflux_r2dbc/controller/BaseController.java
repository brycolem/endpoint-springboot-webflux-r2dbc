package com.example.springboot_webflux_r2dbc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class BaseController {

    @GetMapping
    public Mono<Map<String, Object>> root(ServerHttpRequest request) {
        String baseUrl = request.getURI().toString();
        baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;

        Map<String, Object> response = new HashMap<>();

        Map<String, Map<String, String>> links = new HashMap<>();
        links.put("self", createLink(baseUrl + "/", "GET"));
        links.put("swagger_docs", createLink(baseUrl + "/docs", "GET"));
        links.put("get_all_applications", createLink(baseUrl + "/application", "GET"));
        links.put("get_application", createLink(baseUrl + "/application/{application_id}", "GET"));
        links.put("create_application", createLink(baseUrl + "/application", "POST"));
        links.put("update_application", createLink(baseUrl + "/application/{application_id}", "PUT"));
        links.put("patch_application", createLink(baseUrl + "/application/{application_id}", "PATCH"));
        links.put("delete_application", createLink(baseUrl + "/application/{application_id}", "DELETE"));

        response.put("_links", links);
        return Mono.just(response); // Return a reactive Mono
    }

    private Map<String, String> createLink(String href, String method) {
        Map<String, String> link = new HashMap<>();
        link.put("href", href);
        link.put("method", method);
        return link;
    }
}
