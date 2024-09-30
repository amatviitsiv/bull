package com.alex.ua.client.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Authenticate dto.
 */
@Data
@AllArgsConstructor
public class AuthenticateDto {
    private String initData;
    private String tgPlatform;
    private String tgVersion;
}
