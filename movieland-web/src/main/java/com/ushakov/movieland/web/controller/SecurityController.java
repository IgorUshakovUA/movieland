package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    private SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PutMapping(value = "/v1/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SecurityToken login(Credentials credentials) {
        SecurityToken securityToken = securityService.logon(credentials);

        if (securityToken == null) {
            throw new BadRequestException("Invalid username or password!");
        }

        return securityToken;
    }

    @DeleteMapping(value = "/v1/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SecurityToken logout(@RequestParam(name = "uuid") String uuid) {
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
