package com.znaji.urlshortener.domain.model;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlForm(
                                 @NotBlank(message = "Original Url can't be blank")
                                 String originalUrl) {
}
