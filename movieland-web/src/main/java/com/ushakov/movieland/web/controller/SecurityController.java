package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {
    private SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(value = "/v1/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SecurityToken login(@RequestBody Credentials credentials) {
        SecurityToken securityToken = securityService.logon(credentials);

        if (securityToken == null) {
            throw new BadRequestException("Invalid username or password!");
        }

        return securityToken;
    }

    @DeleteMapping(value = "/v1/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SecurityToken logout(@RequestHeader(name = "uuid") String uuid) {
        SecurityToken securityToken = securityService.logout(uuid);

        if (securityToken == null) {
            throw new BadRequestException("Invalid uuid!");
        }

        return securityToken;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
