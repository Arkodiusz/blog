package com.jedrzejewski.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Response {
    private final int status;
    private final Object body;
}
