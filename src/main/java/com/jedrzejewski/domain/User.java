package com.jedrzejewski.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class User {
    Integer userid;
    @NonNull String username;
    @NonNull String password;
    @NonNull String permission;
    @NonNull String readonly;
}
