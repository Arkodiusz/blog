package com.jedrzejewski.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Representation of server response
 */
@Getter
@RequiredArgsConstructor
public class Response {
    private final int status;
    private final Object body;
}
