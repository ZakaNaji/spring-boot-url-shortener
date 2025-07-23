package com.znaji.urlshortener.controller;

import com.znaji.urlshortener.domain.entity.ShortUrl;
import com.znaji.urlshortener.repository.ShortUrlRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/")
public class HomeController {

    private final ShortUrlRepository shortUrlRepository;

    public HomeController(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @GetMapping
    public String hello(Model model) {
        List<ShortUrl> urls = shortUrlRepository.findAllPublicUrls();
        model.addAttribute("shortUrls", urls);
        model.addAttribute("baseUrl", "http://localhost:8080");
        return "Hello";
    }
}
