package com.znaji.urlshortener.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.znaji.urlshortener.domain.entity.ShortUrl;
import com.znaji.urlshortener.domain.model.ShortUrlDto;
import com.znaji.urlshortener.domain.model.UserDto;
import com.znaji.urlshortener.services.ShortUrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller()
@RequestMapping("/")
public class HomeController {


    private final ShortUrlService shortUrlService;

    public HomeController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping
    public String hello(Model model) {
        List<ShortUrlDto> urls = shortUrlService.findAllPublicUrls();
        model.addAttribute("shortUrls", urls);
        model.addAttribute("baseUrl", "http://localhost:8080");
        return "Hello";
    }

    @GetMapping("/manual-json")
    public void manuallyCreatedJson(HttpServletResponse response) throws IOException {
        UserDto user = new UserDto(1l, "znaji@mail.com", "ZikasNaji");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        System.out.println(userJson);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(userJson);

    }
}
