package com.znaji.urlshortener.web.controllers;

import com.znaji.urlshortener.domain.entity.ShortUrl;
import com.znaji.urlshortener.services.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<ShortUrl> urls = shortUrlService.findAllPublicUrls();
        model.addAttribute("shortUrls", urls);
        model.addAttribute("baseUrl", "http://localhost:8080");
        return "Hello";
    }
}
