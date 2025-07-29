package com.znaji.urlshortener.domain.repository;

import com.znaji.urlshortener.domain.entity.ShortUrl;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    //@Query("select su from ShortUrl as su JOIN FETCH su.createdBy where su.isPrivate = false order by su.createdAt desc")
    @Query("select su from ShortUrl su where su.isPrivate = false order by su.createdAt desc ")
    @EntityGraph(attributePaths = "createdBy")
    List<ShortUrl> findAllPublicUrls();

    boolean existsByShortKey(String shortKey);

    Optional<ShortUrl> findByShortKey(String shortKey);
}
