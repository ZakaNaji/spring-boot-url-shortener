package com.znaji.urlshortener.services;

import com.znaji.urlshortener.domain.entity.ShortUrl;
import com.znaji.urlshortener.domain.model.ShortUrlDto;
import com.znaji.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final EntityMapper entityMapper;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper) {
        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
    }

    public List<ShortUrlDto> findAllPublicUrls() {
        return shortUrlRepository.findAllPublicUrls().stream()
                .map(entityMapper::toShortUrlDto)
                .toList();
    }
}
