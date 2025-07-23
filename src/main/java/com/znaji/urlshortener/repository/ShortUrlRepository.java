package com.znaji.urlshortener.repository;

import com.znaji.urlshortener.domain.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    List<ShortUrl> findShortUrlByIsPrivateFalseOrderByCreatedAtDesc();
}
