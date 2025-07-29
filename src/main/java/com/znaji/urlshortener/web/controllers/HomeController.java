package com.znaji.urlshortener.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.znaji.urlshortener.domain.config.ApplicationProperties;
import com.znaji.urlshortener.domain.exception.InvalidShortUrlKeyException;
import com.znaji.urlshortener.domain.model.CreateShortUrlForm;
import com.znaji.urlshortener.domain.model.ShortUrlDto;
import com.znaji.urlshortener.domain.model.UserDto;
import com.znaji.urlshortener.domain.services.ShortUrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller()
@RequestMapping("/")
public class HomeController {


    private final ShortUrlService shortUrlService;
    private final ApplicationProperties applicationProperties;


    public HomeController(ShortUrlService shortUrlService, ApplicationProperties applicationProperties) {
        this.shortUrlService = shortUrlService;
        this.applicationProperties = applicationProperties;
    }

    @GetMapping
    public String hello(Model model) {
        List<ShortUrlDto> urls = shortUrlService.findAllPublicUrls();
        model.addAttribute("shortUrls", urls);
        model.addAttribute("baseUrl", applicationProperties.baseUrl());
        model.addAttribute("createShortUrlForm", new CreateShortUrlForm(""));
        return "index";
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

    @PostMapping("/short-urls")
    public String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm shortUrlForm,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (result.hasErrors()) {
            List<ShortUrlDto> urls = shortUrlService.findAllPublicUrls();
            model.addAttribute("shortUrls", urls);
            model.addAttribute("baseUrl", applicationProperties.baseUrl());
            return "index";
        }

        try {
            ShortUrlDto shortUrlDto = shortUrlService.createShortUrl(shortUrlForm);
            redirectAttributes.addFlashAttribute("successMessage", "Short URL created successfully "+
                    applicationProperties.baseUrl() + "/s/" + shortUrlDto.shortKey());

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short URL : " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/s/{shortKey}")
    public String redirectToOriginalUrl(@PathVariable("shortKey") String shortKey) {
        Optional<ShortUrlDto> shortUrlDto = shortUrlService.findShortUrlByKey(shortKey);
        if (shortUrlDto.isEmpty()) {
            throw new InvalidShortUrlKeyException("invalid short key: " + shortKey);
        }
        return "redirect:" + shortUrlDto.get().originalUrl();
    }

}
