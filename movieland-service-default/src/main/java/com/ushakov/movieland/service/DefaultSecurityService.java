package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityItem;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.dao.SecurityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultSecurityService implements SecurityService {
    @Value("${security.time-to-live.hours}")
    private int timeToLiveHours;

    private SecurityDao securityDao;

    private ConcurrentHashMap<String, SecurityItem> securityItems = new ConcurrentHashMap<>();

    @Autowired
    public DefaultSecurityService(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }

    @Override
    public SecurityToken logon(Credentials credentials) {
        SecurityToken securityToken = securityDao.logon(credentials);
        SecurityItem securityItem = new SecurityItem(securityToken);
        securityItems.put(securityItem.getUuid(), securityItem);

        return securityToken;
    }

    @Override
    public SecurityToken logout(String uuid) {
        SecurityItem securityItem = securityItems.get(uuid);
        if(securityItem != null) {
            securityItem.setAlive(false);
            return securityItem.getSecurityToken();
        }

        return null;
    }

    @Override
    public boolean isLoggedOn(String uuid) {
        SecurityItem securityItem = securityItems.get(uuid);

        return securityItem != null && securityItem.isAlive();
    }

    @Scheduled(cron = "${security.cron}")
    public void removeExpiredSecurityTokens() {
        Iterator<SecurityItem> itemIterator = securityItems.values().iterator();
        while (itemIterator.hasNext()) {
            SecurityItem current = itemIterator.next();
            if (current.getCreated().plusHours(timeToLiveHours).isAfter(LocalDateTime.now()) || !current.isAlive()) {
                itemIterator.remove();
            }
        }
    }

    Map<String, SecurityItem> getSecurityItems() {
        return  Collections.unmodifiableMap( securityItems);
    }
}