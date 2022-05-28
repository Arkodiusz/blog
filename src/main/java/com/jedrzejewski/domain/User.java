package com.jedrzejewski.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Domain object representing row from 'user' table.
 *
 * I have not used any wrapper (e.g. DTO) because of simplicity of domain objects.
 * I decided not to implement another layer of code (dtos and mappers)
 *
 * I am using Lombok @Annotations to reduce boilerplate code and focus on application features.
 *
 * @Data generates code needed in POJOs and beans:
 * getters, setters for all non-final fields, toString, equals and hashCode implementations that involve the fields of the class
 * and constructor with required arguments
 *
 * @Builder provides all features for builder design pattern
 */
@Data
@Builder
public class User {
    Integer userid;
    @NonNull String username;
    @NonNull String password;
    @NonNull String permission;
    @NonNull String readonly;
}
