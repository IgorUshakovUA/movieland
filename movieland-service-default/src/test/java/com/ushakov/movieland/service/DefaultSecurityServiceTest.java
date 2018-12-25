package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityItem;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.common.UserRole;
import com.ushakov.movieland.dao.SecurityDao;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultSecurityServiceTest {

    @Test
    public void testLogonAndIsLoggedOnAndLogout() {
        // Prep
        SecurityDao securityDao = mock(SecurityDao.class);

        SecurityToken expectedSecurityTocken = new SecurityToken();
        expectedSecurityTocken.setUuid(UUID.randomUUID().toString());
        expectedSecurityTocken.setNickName("my nick name");
        expectedSecurityTocken.setUserRole(UserRole.USER);
        expectedSecurityTocken.setId(1);

        Credentials credentials = new Credentials("my@email.com", "my_password");

        // When
        when(securityDao.logon(any(Credentials.class))).thenReturn(expectedSecurityTocken);

        // Then
        SecurityService securityService = new DefaultSecurityService(securityDao);
        SecurityToken actualSecurityToken = securityService.logon(credentials);

        assertEquals(expectedSecurityTocken, actualSecurityToken);
        assertTrue(securityService.isLoggedOn(actualSecurityToken.getUuid()));

        securityService.logout(actualSecurityToken.getUuid());

        assertFalse(securityService.isLoggedOn(actualSecurityToken.getUuid()));
    }

    @Test
    public void testRemoveExpiredSecurityTokens() {
        // Prepare
        SecurityDao securityDao = mock(SecurityDao.class);

        SecurityToken securityToken1 = new SecurityToken();
        securityToken1.setUuid(UUID.randomUUID().toString());
        securityToken1.setNickName("my nick name 1");
        securityToken1.setUserRole(UserRole.USER);
        securityToken1.setId(1);

        SecurityToken securityToken2 = new SecurityToken();
        securityToken2.setUuid(UUID.randomUUID().toString());
        securityToken2.setNickName("my nick name 2");
        securityToken2.setUserRole(UserRole.USER);
        securityToken2.setId(2);

        SecurityToken securityToken3 = new SecurityToken();
        securityToken3.setUuid(UUID.randomUUID().toString());
        securityToken3.setNickName("my nick name 3");
        securityToken3.setUserRole(UserRole.USER);
        securityToken3.setId(3);

        Credentials credentials = new Credentials("my@email.com", "my_password");

        // When
        when(securityDao.logon(any(Credentials.class))).thenReturn(securityToken1).thenReturn(securityToken2).thenReturn(securityToken3);

        DefaultSecurityService securityService = new DefaultSecurityService(securityDao);
        securityService.logon(credentials);
        securityService.logon(credentials);
        securityService.logon(credentials);

        Map<String, SecurityItem> securityItems = securityService.getSecurityItems();
        securityItems.get(securityToken1.getUuid()).setCreated(LocalDateTime.now().plusHours(24));
        securityItems.get(securityToken2.getUuid()).setCreated(LocalDateTime.now().plusHours(24));

        // Then
        securityService.removeExpiredSecurityTokens();

        assertFalse(securityService.isLoggedOn(securityToken1.getUuid()));
        assertFalse(securityService.isLoggedOn(securityToken2.getUuid()));
        assertTrue(securityService.isLoggedOn(securityToken3.getUuid()));
    }

}