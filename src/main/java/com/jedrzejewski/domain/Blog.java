package com.jedrzejewski.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
I am using Lombok @Annotations to reduce boilerplate code and focus on application features.

@Data generates code needed in POJOs and beans:
getters, setters for all non-final fields, toString, equals and hashCode implementations that involve the fields of the class
and constructor with required arguments

@Builder provides all features for builder design pattern
*/

@Data
@Builder
public class Blog {

    Integer id;
    @NonNull String text;
    @NonNull Integer userid;
}
