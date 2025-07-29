package com.znaji.urlshortener.domain.services;

import com.znaji.urlshortener.domain.config.ApplicationProperties;
import com.znaji.urlshortener.domain.entity.ShortUrl;
import com.znaji.urlshortener.domain.model.CreateShortUrlForm;
import com.znaji.urlshortener.domain.model.ShortUrlDto;
import com.znaji.urlshortener.domain.repository.ShortUrlRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final EntityMapper entityMapper;
    private final ApplicationProperties applicationProperties;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper, ApplicationProperties applicationProperties) {
        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
        this.applicationProperties = applicationProperties;
    }

    public List<ShortUrlDto> findAllPublicUrls() {
        return shortUrlRepository.findAllPublicUrls().stream()
                .map(entityMapper::toShortUrlDto)
                .toList();
    }

    public ShortUrlDto createShortUrl(CreateShortUrlForm shortUrlForm) {
        if (applicationProperties.validateUrlExistence()) {
            if (!UrlExistenceValidation.urlExists(shortUrlForm.originalUrl())) {
                throw new IllegalArgumentException("invalid url " + shortUrlForm.originalUrl());
            }
        }
        var shortKey = generateUniqueShortKey();
        var shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(shortUrlForm.originalUrl());
        shortUrl.setShortKey(shortKey);
        shortUrl.setCreatedBy(null);
        shortUrl.setPrivate(false);
        shortUrl.setClickCount(0L);
        shortUrl.setExpiresAt(Instant.now().plus(applicationProperties.defaultExpireInDays(), DAYS));
        shortUrl.setCreatedAt(Instant.now());
        shortUrlRepository.save(shortUrl);
        return entityMapper.toShortUrlDto(shortUrl);
    }

    private String generateUniqueShortKey() {
        String shortKey;
        do {
            shortKey = generateRandomShortKey();
        } while (shortUrlRepository.existsByShortKey(shortKey));
        return shortKey;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_KEY_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomShortKey() {
        StringBuilder sb = new StringBuilder(SHORT_KEY_LENGTH);
        for (int i = 0; i < SHORT_KEY_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
