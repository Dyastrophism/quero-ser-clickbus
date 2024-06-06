package com.clickbus.alpha.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public record Place(
        @Id
        Long id,
        @NotBlank
        String name,
        String slug,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @CreatedDate
        LocalDateTime createdAt,
        @LastModifiedDate
        LocalDateTime updatedAt
) {
}
