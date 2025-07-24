package com.znaji.urlshortener.domain.model;

import com.znaji.urlshortener.domain.entity.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDto(Long id, String email, String name) implements Serializable {
}