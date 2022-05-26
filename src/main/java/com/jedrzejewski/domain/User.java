package com.jedrzejewski.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    Integer userid;
    String username;
    String password;
    String permission;
    String readonly;
}
