package com.znaji.urlshortener.domain.model;

import com.znaji.urlshortener.domain.entity.ShortUrl;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link ShortUrl}
 */
public record ShortUrlDto(Long id, String shortKey, String originalUrl, UserDto createdBy, Boolean isPrivate,
                          Instant createdAt, Instant expiresAt, Long clickCount) implements Serializable {
}