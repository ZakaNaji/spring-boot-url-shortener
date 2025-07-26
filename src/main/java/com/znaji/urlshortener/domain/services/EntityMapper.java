package com.znaji.urlshortener.domain.services;

import com.znaji.urlshortener.domain.entity.ShortUrl;
import com.znaji.urlshortener.domain.entity.User;
import com.znaji.urlshortener.domain.model.ShortUrlDto;
import com.znaji.urlshortener.domain.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public ShortUrlDto toShortUrlDto(ShortUrl shortUrl) {
        UserDto userDto = null;
        if(shortUrl.getCreatedBy() != null) {
            userDto = toUserDto(shortUrl.getCreatedBy());
        }

        return new ShortUrlDto(
                shortUrl.getId(),
                shortUrl.getShortKey(),
                shortUrl.getOriginalUrl(),
                userDto,
                shortUrl.getPrivate(),
                shortUrl.getCreatedAt(),
                shortUrl.getExpiresAt(),
                shortUrl.getClickCount()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }
}
