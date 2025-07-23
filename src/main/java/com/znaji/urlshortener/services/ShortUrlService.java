package com.znaji.urlshortener.services;

import com.znaji.urlshortener.domain.entity.ShortUrl;
import com.znaji.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public List<ShortUrl> findAllPublicUrls() {
        return shortUrlRepository.findAllPublicUrls();
    }
}
